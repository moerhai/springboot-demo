package com.mohai.one.springbootsecuritydynamic.config;

import com.mohai.one.springbootsecuritydynamic.filter.DynamicallyUrlFilterSecurityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.*;

/**
 *
 * @Auther: moerhai@qq.com
 * @Date: 2020/9/26 09:57
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public DynamicallyUrlFilterSecurityInterceptor dynamicallyUrlInterceptor()  throws Exception {
        DynamicallyUrlFilterSecurityInterceptor interceptor = new DynamicallyUrlFilterSecurityInterceptor();
        LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> requestMap = new LinkedHashMap<>();

        interceptor.setSecurityMetadataSource(new MyFilterSecurityMetadataSource(requestMap));
        // 配置 RoleVoter 决策
        List<AccessDecisionVoter<? extends Object>> decisionVoters = new ArrayList<AccessDecisionVoter<? extends Object>>();
        decisionVoters.add(new RoleVoter());
        // 设置认证决策管理器
        interceptor.setAccessDecisionManager(new DynamicallyUrlAccessDecisionManager(decisionVoters));
        // 设置身份认证管理器
        interceptor.setAuthenticationManager(authenticationManagerBean());
        interceptor.afterPropertiesSet();
        return interceptor;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 添加后置过滤器
        http.addFilterAfter(dynamicallyUrlInterceptor(), FilterSecurityInterceptor.class)
                .authorizeRequests() // 需要授权的权限路径
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated() // 配置所以其他未配置的路径需要认证
                .and()
                .formLogin().loginPage("/login").permitAll() // 登录页面
                .and()
                .logout().permitAll(); // 登出权限
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
//                .exceptionHandling()
//                .authenticationEntryPoint(problemSupport)
//                .accessDeniedHandler(problemSupport)
//                .and()
//                .csrf()
//                .disable()
//                .headers()
//                .frameOptions()
//                .disable()
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authorizeRequests()
//                // 自定义accessDecisionManager
//                .accessDecisionManager(accessDecisionManager())
//
//                .and()
//                .apply(securityConfigurerAdapter());
//
//    }


    @Bean
    public AccessDecisionManager accessDecisionManager() {
        List<AccessDecisionVoter<? extends Object>> decisionVoters
                = Arrays.asList(
                new WebExpressionVoter(),
                // new RoleVoter(),
                new RoleBasedVoter(),
                new AuthenticatedVoter());
        return new UnanimousBased(decisionVoters);
    }

}