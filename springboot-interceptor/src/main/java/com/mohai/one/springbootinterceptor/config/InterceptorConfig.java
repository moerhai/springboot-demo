package com.mohai.one.springbootinterceptor.config;

import com.mohai.one.springbootinterceptor.interceptor.FirstHandlerInterceptor;
import com.mohai.one.springbootinterceptor.interceptor.SecondHandlerInterceptor;
import com.mohai.one.springbootinterceptor.service.IndexService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        FirstHandlerInterceptor firstHandlerInterceptor = new FirstHandlerInterceptor(new IndexService());
        registry.addInterceptor(firstHandlerInterceptor).addPathPatterns("/**");
        SecondHandlerInterceptor secondHandlerInterceptor = new SecondHandlerInterceptor();
        registry.addInterceptor(secondHandlerInterceptor).addPathPatterns("/**");
    }

}
