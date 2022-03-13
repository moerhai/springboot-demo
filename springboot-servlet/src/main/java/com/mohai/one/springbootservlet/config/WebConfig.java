package com.mohai.one.springbootservlet.config;

import com.mohai.one.springbootservlet.filter.FirstFilter;
import com.mohai.one.springbootservlet.listener.FirstListener;
import com.mohai.one.springbootservlet.servlet.FirstServlet;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletContextListener;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

@Configuration
public class WebConfig {

    /**
     * servletRegistrationBean:(使用代码注册Servlet（不需要@ServletComponentScan注解）).
     */
    @Bean
    public ServletRegistrationBean getFirstServlet(){
        ServletRegistrationBean registrationBean = new ServletRegistrationBean();
        registrationBean.setServlet(new FirstServlet());
        List<String> urlMappings = new ArrayList<String>();
        // 访问，可以添加多个
        urlMappings.add("/first");
        registrationBean.setUrlMappings(urlMappings);
        // 设置加载顺序
        registrationBean.setLoadOnStartup(1);
        return registrationBean;
    }

    /**
     * 注册拦截器
     */
    @Bean
    public FilterRegistrationBean getFirstFilter(){
        FirstFilter demoFilter = new FirstFilter();
        FilterRegistrationBean registrationBean=new FilterRegistrationBean();
        registrationBean.setFilter(demoFilter);
        List<String> urlPatterns=new ArrayList<String>();
        //拦截路径，可以添加多个
        urlPatterns.add("/*");
        registrationBean.setUrlPatterns(urlPatterns);
        //设置注册顺序
        registrationBean.setOrder(1);
        return registrationBean;
    }

    /**
     * 注册监听器
     */
    @Bean
    public ServletListenerRegistrationBean<ServletContextListener> getFirstListener(){
        ServletListenerRegistrationBean<ServletContextListener> registrationBean = new ServletListenerRegistrationBean<>();
        registrationBean.setListener(new FirstListener());
        // 设置注册顺序
        registrationBean.setOrder(1);
        return registrationBean;
    }

}