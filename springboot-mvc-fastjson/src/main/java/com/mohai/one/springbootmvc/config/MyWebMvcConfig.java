package com.mohai.one.springbootmvc.config;


import com.mohai.one.springbootmvc.converter.MyMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * 使用注解@EnableWebMvc开启SpringMVC功能，
 * 最终通过WebMvcConfigurationSupport来完成SpringMVC默认的配置
 */
//@EnableWebMvc
//@Configuration
public class MyWebMvcConfig implements WebMvcConfigurer {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        MyMessageConverter myMessageConverter = new MyMessageConverter();
        converters.add(myMessageConverter);
    }
}