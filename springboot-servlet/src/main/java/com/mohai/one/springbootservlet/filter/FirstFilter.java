package com.mohai.one.springbootservlet.filter;

import javax.servlet.*;
import java.io.IOException;

public class FirstFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("FirstFilter正在执行doFilter");
        chain.doFilter(request, response);//放行
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("FirstFilter正在执行init");
    }

    @Override
    public void destroy() {
        System.out.println("FirstFilter正在执行destroy");
    }
}
