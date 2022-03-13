package com.mohai.one.springbootsecurity.event;

import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.core.Authentication;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/9/17 00:46
 */
public class UserLoginFailedEvent extends AbstractAuthenticationEvent {

    public UserLoginFailedEvent(Authentication authentication) {
        super(authentication);
    }
}
