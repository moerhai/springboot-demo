package com.mohai.one.oauth2ssoserver.service;

import com.alibaba.fastjson.JSON;
import com.mohai.one.oauth2ssoserver.domain.MyUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/10/5 10:40
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(MyUserDetailsService.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private PermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        SysUser sysUser = userService.getByUsername(username);
//        if (null == sysUser) {
//            log.warn("用户{}不存在", username);
//            throw new UsernameNotFoundException(username);
//        }
//        List<SysPermission> permissionList = permissionService.findByUserId(sysUser.getId());
//        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
//        if (!CollectionUtils.isEmpty(permissionList)) {
//            for (SysPermission sysPermission : permissionList) {
//                authorityList.add(new SimpleGrantedAuthority(sysPermission.getCode()));
//            }
//        }
//
//        MyUser myUser = new MyUser(sysUser.getUsername(), passwordEncoder.encode(sysUser.getPassword()), authorityList);
//
//        log.info("登录成功！用户: {}", JSON.toJSONString(myUser));
//
//        return myUser;
        return null;
    }
}