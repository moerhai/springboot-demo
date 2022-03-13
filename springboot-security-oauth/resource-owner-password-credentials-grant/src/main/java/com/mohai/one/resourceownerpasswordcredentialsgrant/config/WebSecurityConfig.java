package com.mohai.one.resourceownerpasswordcredentialsgrant.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/10/3 17:28
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password(passwordEncoder().encode("123456"))
                .authorities("ADMIN")
                .and()
                .withUser("user")
                .password(passwordEncoder().encode("123456"))
                .authorities("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 所有请求都需要通过认证
        http.authorizeRequests().anyRequest().authenticated()
                .and()
                .httpBasic() // httpBasic形式登录，页面弹框
                .and()
                .csrf().disable(); //关跨域保护
    }
}