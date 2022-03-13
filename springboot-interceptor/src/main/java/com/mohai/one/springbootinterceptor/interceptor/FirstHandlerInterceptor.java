package com.mohai.one.springbootinterceptor.interceptor;

import com.mohai.one.springbootinterceptor.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FirstHandlerInterceptor implements HandlerInterceptor {

    private IndexService indexService;

    public FirstHandlerInterceptor(IndexService indexService){
        this.indexService = indexService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("FirstHandlerInterceptor正在执行preHandle");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        indexService.testIndex();
        System.out.println("FirstHandlerInterceptor正在执行postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("FirstHandlerInterceptor正在执行afterCompletion");
    }
}
