package com.mohai.one.app.core.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/12/25 23:38
 */
@Component
public class LogoutUserSuccessHandler implements LogoutSuccessHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogoutUserSuccessHandler.class);
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        User userDetails = (User) authentication.getPrincipal();
        LOGGER.debug("[{}]成功登出！",userDetails.getUsername());

    }
}
