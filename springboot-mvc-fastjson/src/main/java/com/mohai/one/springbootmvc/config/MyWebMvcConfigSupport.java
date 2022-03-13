package com.mohai.one.springbootmvc.config;

import com.mohai.one.springbootmvc.converter.MyMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * 在程序中只会生效一个，用来代替@EnableWebMvc
 * 如果用户已经实现了WebMvcConfigurationSupport，
 * 那么基于WebMvcConfigurer的所有实现类将不会生效
 */
//@Configuration
public class MyWebMvcConfigSupport extends WebMvcConfigurationSupport {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        MyMessageConverter myMessageConverter = new MyMessageConverter();
        converters.add(myMessageConverter);
    }
}
