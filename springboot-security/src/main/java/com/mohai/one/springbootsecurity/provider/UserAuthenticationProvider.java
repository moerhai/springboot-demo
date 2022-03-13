package com.mohai.one.springbootsecurity.provider;

import com.mohai.one.springbootsecurity.event.UserLoginFailedEvent;
import com.mohai.one.springbootsecurity.service.UserAuthDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Collection;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/9/17 00:40
 */
//@Component
public class UserAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserAuthDetailsService userAuthDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ApplicationEventPublisher publisher;

    /**
     * 认证处理逻辑
     * 返回一个Authentication的实现类则表示认证成功，返回null则表示认证失败
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        if(StringUtils.isEmpty(username)){
            throw new UsernameNotFoundException("username用户名不能为空");
        }
        if(StringUtils.isEmpty(password)){
            throw new BadCredentialsException("password密码不能为空");
        }
        //获取用户信息
        UserDetails user = userAuthDetailsService.loadUserByUsername(username);
        //比较前端传入的密码明文和数据库中加密的密码是否相等
        if (!passwordEncoder.matches(password, user.getPassword())) {
            //发布密码不正确事件
            publisher.publishEvent(new UserLoginFailedEvent(authentication));
            throw new BadCredentialsException("password密码不正确");
        }
        //获取用户权限信息
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        return new UsernamePasswordAuthenticationToken(user, password, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
