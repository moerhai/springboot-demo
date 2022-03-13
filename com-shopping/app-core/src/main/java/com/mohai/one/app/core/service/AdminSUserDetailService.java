package com.mohai.one.app.core.service;

import com.mohai.one.app.core.exception.BadRequestException;
import com.mohai.one.app.core.model.AdminSUser;
import com.mohai.one.app.core.user.domain.AdminPermission;
import com.mohai.one.app.core.user.domain.AdminUserInfo;
import com.mohai.one.app.core.user.dao.AdminPermissionMapper;
import com.mohai.one.app.core.user.dao.AdminUserInfoMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 基于数据库加载用户信息
 * @Auther: moerhai@qq.com
 * @Date: 2020/11/24 00:34
 */
@Service("userDetailsService")
public class AdminSUserDetailService implements UserDetailsService {

    private static final Logger LOG = LoggerFactory.getLogger(AdminSUserDetailService.class);

    @Autowired
    private AdminUserInfoMapper adminUserInfoMapper;
    @Autowired
    private AdminPermissionMapper adminPermissionMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOG.debug("正在加载[{}]用户信息",username);
        if(StringUtils.isBlank(username)){
            throw new BadRequestException("用户名不能为空");
        }
        AdminUserInfo adminUserInfo = adminUserInfoMapper.findByName(username);
        if(adminUserInfo == null){
            throw new UsernameNotFoundException("用户不存在");
        }
        if (!adminUserInfo.isEnable()) {
            throw new DisabledException("账号状态异常");
        }
        Collection<? extends GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(getUserPermissions(adminUserInfo).toArray(new String[0]));
        AdminSUser user = new AdminSUser(adminUserInfo.getUsername(), adminUserInfo.getPassword(),authorities);
        user.setUserId(String.valueOf(adminUserInfo.getUserId()));
        user.setMobile(adminUserInfo.getMobile());
        user.setEmail(adminUserInfo.getEmail());
        return user;
    }

    private Set<String> getUserPermissions(AdminUserInfo adminUserInfo) {
        List<AdminPermission> permsList = adminPermissionMapper.selectPermListByUserId(adminUserInfo.getUserId());
        Set<String> permsSet = permsList.stream().flatMap((perms)->{
                    if (perms == null)
                        return null;
                    return Arrays.stream(perms.getPermCode().trim().split(","));
                }
        ).collect(Collectors.toSet());
        return permsSet;
    }
}