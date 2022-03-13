package com.mohai.one.springbootsecuritydynamic.config;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 配置认证数据源
 * 通过URL地址获取相应权限然后再获取相应的角色集合
 * @Auther: moerhai@qq.com
 * @Date: 2020/9/26 08:42
 */
public class MyFilterSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private final Map<RequestMatcher, Collection<ConfigAttribute>> requestMap;

    /**
     * 在构造方法里初始化url权限数据，
     * 只要保证在 getAttributes() 之前初始好数据就可以了
     *
     * @param requestMap
     */
    public MyFilterSecurityMetadataSource(
            LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> requestMap) {
        // 初始化权限数据
        this.requestMap = requestMap;
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        FilterInvocation fi = (FilterInvocation) object;
        HttpServletRequest request = fi.getRequest();
//        String url = fi.getRequestUrl();
//        String httpMethod = fi.getRequest().getMethod();

        // Lookup your database (or other source) using this information and populate the
        // list of attributes
        // 遍历初始化的权限数据，找到对应的url对应的权限
        for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : requestMap
                .entrySet()) {
            if (entry.getKey().matches(request)) {
                return entry.getValue();
            }
        }


        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        // 返回true表示支持当前格式类型
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
