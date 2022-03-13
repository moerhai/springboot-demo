package com.mohai.one.springbootcors.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")//指定可以被跨域的路径
                .allowedHeaders("*")  //服务器允许的请求头
                .allowedMethods("POST", "PUT", "GET", "OPTIONS", "DELETE")  //服务器允许的请求方法
                .allowCredentials(true)  //允许带cookie的跨域请求 Access-Control-Allow-Credentials
                .allowedOrigins("*")  //服务端允许哪些域请求来源
                .maxAge(3600);   //预检请求的客户端缓存时间 单位秒，默认为1800秒
    }

}
