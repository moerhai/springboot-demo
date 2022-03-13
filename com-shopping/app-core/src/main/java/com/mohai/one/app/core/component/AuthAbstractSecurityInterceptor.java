package com.mohai.one.app.core.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 动态权限拦截器
 * @Auther: moerhai@qq.com
 * @Date: 2020/12/19 02:08
 */
//@Component
public class AuthAbstractSecurityInterceptor extends AbstractSecurityInterceptor implements Filter {

    /**
     * 第一次调用标识
     */
    private static final String FILTER_APPLIED = "__spring_security_authAbstractSecurityInterceptor_filterApplied";

    @Autowired
    private FilterInvocationSecurityMetadataSource securityMetadataSource;

    @Autowired
    public void setAuthAccessDecisionManager(AuthAccessDecisionManager authAccessDecisionManager) {
        super.setAccessDecisionManager(authAccessDecisionManager);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        // 为确保每个请求仅经过一次过滤器：主要由于spring容器托管的bean，都会自动加入到servlet的filter chain中，
        // 而在WebSecurityConfig配置中又把filter注册到了spring security的容器中，所以会出现执行两次的情况。
        if (httpRequest.getAttribute(FILTER_APPLIED) != null) {
          chain.doFilter(httpRequest, httpResponse);
          return;
        }
        httpRequest.setAttribute(FILTER_APPLIED, Boolean.TRUE);
        FilterInvocation fi = new FilterInvocation(request, response, chain);
        // 调用securityMetadataSource的getAttributes(Object object)方法获取url对应的所有权限
        // 再调用AuthAccessDecisionManager的decide方法来校验用户的权限是否足够
        InterceptorStatusToken token = super.beforeInvocation(fi);
        try {
            //执行下一个拦截器
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        } finally {
            super.afterInvocation(token, null);
        }
    }

    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return this.securityMetadataSource;
    }
}