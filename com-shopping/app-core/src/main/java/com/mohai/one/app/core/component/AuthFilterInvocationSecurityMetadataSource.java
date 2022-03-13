package com.mohai.one.app.core.component;

import com.mohai.one.app.core.user.domain.AdminPermission;
import com.mohai.one.app.core.user.dao.AdminPermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 安全元数据源
 * 自定义SecurityMetadataSource，实现从数据库加载ConfigAttribute
 * @Auther: moerhai@qq.com
 * @Date: 2020/12/19 01:56
 */
@Component
public class AuthFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Autowired
    private AdminPermissionMapper adminPermissionMapper;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        //获取请求地址
        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        //查询具体某个接口的权限
        List<AdminPermission> permissionList =  adminPermissionMapper.selectPermListByPath(requestUrl);
        if(permissionList == null || permissionList.size() == 0){
            //请求路径没有配置权限，表明该请求接口可以任意访问
            return null;
        }
        List<String> permissions = permissionList.stream().map(p -> p.getPermCode()).collect(Collectors.toList());
        String[] attributes = permissions.stream().toArray(String[]::new);
        return SecurityConfig.createList(attributes);
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        //是否支持该方法，一般返回true即可
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}