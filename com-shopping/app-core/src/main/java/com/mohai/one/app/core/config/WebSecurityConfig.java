package com.mohai.one.app.core.config;

import com.mohai.one.app.core.component.*;
import com.mohai.one.app.core.filter.JwtTokenAuthenticationFilter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.actuate.context.ShutdownEndpoint;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Spring Security配置
 * @Auther: moerhai@qq.com
 * @Date: 2020/11/22 01:13
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter;

//    private final LoginAuthSuccessHandler loginAuthSuccessHandler;
//    private final LoginAuthFailedHandler loginAuthFailedHandler;
//    private final LogoutUserSuccessHandler logoutUserSuccessHandler;

//    private final AuthAccessDecisionManager authAccessDecisionManager;
//    private final AuthFilterInvocationSecurityMetadataSource authFilterInvocationSecurityMetadataSource;
    /**
     * app.access.ignore.urls
     */
    @Value("${app.access.ignore-urls}")
    private String ignoreUrls;

    public WebSecurityConfig(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, JwtAccessDeniedHandler jwtAccessDeniedHandler, JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter) {
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
        this.jwtTokenAuthenticationFilter = jwtTokenAuthenticationFilter;
//        this.loginAuthSuccessHandler = loginAuthSuccessHandler;
//        this.loginAuthFailedHandler = loginAuthFailedHandler;
//        this.logoutUserSuccessHandler = logoutUserSuccessHandler;
//        this.authAccessDecisionManager = authAccessDecisionManager;
//        this.authFilterInvocationSecurityMetadataSource = authFilterInvocationSecurityMetadataSource;
    }

//    @Bean
//    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
//        // 去除 ROLE_ 前缀
//        return new GrantedAuthorityDefaults("");
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        // 获取登录用户信息
//        return new AdminSUserDetailService();
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        List<String> urlList = new ArrayList<>();
        if(!StringUtils.isBlank(ignoreUrls)){
            urlList = Arrays.asList(ignoreUrls.split(",")).stream().map(s -> s.trim()).collect(Collectors.toList());
        }
        httpSecurity.cors()// 添加Cors过滤器
                .and()
                // 由于使用的是JWT,所以禁用CSRF跨域
                .csrf().disable()
                //设置响应头
                .headers()
                // 禁用缓存
                .cacheControl().disable()
                //防止iframe造成跨域
                .frameOptions().disable()
                .and()
                // 添加 JWT filter
                .addFilterBefore(jwtTokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                // 授权异常
                .exceptionHandling()
                .accessDeniedHandler(jwtAccessDeniedHandler) // 通常会被全局异常处理类拦截掉
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)//匿名用户访问无权限资源时异常处理
                .and()
                // 不创建会话,通过前端传token到后台过滤器中验证是否存在访问权限
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .sessionFixation().none()
                .and()
                // 对请求授权配置
                .authorizeRequests()
                // 允许OPTIONS请求,预检请求
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                // 允许匿名访问
                //.requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                // 允许对于网站静态资源的无授权访问
                .antMatchers(HttpMethod.GET,
                        "/*.html",
                        "/favicon.ico",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/swagger-resources/**",
                        "/v2/api-docs/**",
                        "/webjars/**",
                        "/druid/**"
                ).permitAll()
                // 对登录注册要允许匿名访问
                .antMatchers("/api/auth/login","/api/auth/code","/api/auth/register","/api/auth/logout").permitAll()
                // 自定义
                .antMatchers(urlList.toArray(new String[0])).permitAll()
                // actuator监控权限配置
                .requestMatchers(EndpointRequest.to(ShutdownEndpoint.class)).hasRole("ACTUATOR_ADMIN")
                .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                // 剩余所有请求都需要鉴权
                .anyRequest().authenticated()
                // url权限认证处理,动态权限
//                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
//                    @Override
//                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
//                        o.setAccessDecisionManager(authAccessDecisionManager);//访问决策管理器
//                        o.setSecurityMetadataSource(authFilterInvocationSecurityMetadataSource);//安全元数据源
//                        return o;
//                    }
//                })
                .and()
                .formLogin().permitAll()//登录,允许所有用户
//                .successHandler(loginAuthSuccessHandler)//登录成功处理逻辑
//                .failureHandler(loginAuthFailedHandler)//登录失败处理逻辑
                .and()
                .logout().permitAll();//退出,允许所有用户
//                .logoutSuccessHandler(logoutUserSuccessHandler);//登出成功处理逻辑

      //  httpSecurity.addFilterBefore(authAbstractSecurityInterceptor, FilterSecurityInterceptor.class);
    }

}