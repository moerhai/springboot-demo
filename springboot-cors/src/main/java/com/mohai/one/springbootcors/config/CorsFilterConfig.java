package com.mohai.one.springbootcors.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;


@Configuration
public class CorsFilterConfig {

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        FilterRegistrationBean<CorsFilter> corsFilterFilterRegistrationBean = new FilterRegistrationBean<>();
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        //服务器允许的请求头
        corsConfiguration.addAllowedHeader("*");
        //服务端允许哪些域请求来源
        corsConfiguration.addAllowedOrigin("*");
        //服务器允许的请求方法
        corsConfiguration.setAllowedMethods(Arrays.asList("POST", "PUT", "GET", "OPTIONS", "DELETE"));
        //允许带cookie的跨域请求 Access-Control-Allow-Credentials
        corsConfiguration.setAllowCredentials(true);
        //预检请求的客户端缓存时间 单位秒，默认为1800秒
        corsConfiguration.setMaxAge(3600L);
        //指定可以被跨域的路径
        source.registerCorsConfiguration("/**", corsConfiguration);
        //注册CorsFilter
        corsFilterFilterRegistrationBean.setFilter(new CorsFilter(source));
        //设置加载顺序为-1，该值越小优先级越高
        corsFilterFilterRegistrationBean.setOrder(-1);
        return corsFilterFilterRegistrationBean;
    }

}
