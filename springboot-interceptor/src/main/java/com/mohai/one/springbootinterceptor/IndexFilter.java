package com.mohai.one.springbootinterceptor;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter("/*")
public class IndexFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("IndexFilter已执行");
        filterChain.doFilter(servletRequest, servletResponse);//放行
    }
}
