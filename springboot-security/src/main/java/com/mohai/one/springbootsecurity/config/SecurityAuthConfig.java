package com.mohai.one.springbootsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @Auther: moerhai@qq.com
 * @Date: 2020/9/21 23:56
 */
@EnableWebSecurity
public class SecurityAuthConfig extends WebSecurityConfigurerAdapter {

    // 指定密码的加密方式
    @Bean
    public PasswordEncoder passwordEncoder(){
        // 对密码进行加密
       return new BCryptPasswordEncoder();
    }

    // 配置用户及其对应的角色
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("root").password(passwordEncoder().encode("root")).roles("DBA")
                .and()
                .withUser("admin").password(passwordEncoder().encode("admin")).roles("ADMIN")
                .and()
                .withUser("test").password(passwordEncoder().encode("test")).roles("USER");
    }

    // 配置角色继承关系
    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        String hierarchy = "ROLE_DBA > ROLE_ADMIN > ROLE_USER";
        roleHierarchy.setHierarchy(hierarchy);
        return roleHierarchy;
    }

    // 配置 URL 访问权限
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests() // 开启 HttpSecurity 配置
                .antMatchers("/index/**").hasRole("DBA") // "/index/**" 模式URL需"DBA"角色
                .antMatchers("/admin/**").hasRole("ADMIN") // "/admin/**" 模式URL需"ADMIN"角色
                .antMatchers("/user/**").hasRole("USER") // "/user/**"模式URL需"USER"角色
                .anyRequest().authenticated() // 用户访问其它URL都必须认证后访问（登录后访问）
                .and().formLogin().loginProcessingUrl("/login").permitAll(); // 开启表单登录并配置登录接口
               // .and().csrf().disable(); // 关闭csrf
    }

}
