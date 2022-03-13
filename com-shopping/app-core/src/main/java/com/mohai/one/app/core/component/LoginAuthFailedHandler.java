package com.mohai.one.app.core.component;

import cn.hutool.core.util.CharsetUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mohai.one.app.core.constant.ComErrorCodeEnum;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 登陆失败
 * @Auther: moerhai@qq.com
 * @Date: 2020/12/15 02:02
 */
@Component
public class LoginAuthFailedHandler implements AuthenticationFailureHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginAuthFailedHandler.class);
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        LOGGER.debug("IP:[{}],try to login failed cause:{}", request.getRemoteHost(), exception.getMessage());

        ComErrorCodeEnum errorCodeEnum;
        if (exception instanceof AccountExpiredException) {
            //账号过期
            errorCodeEnum = ComErrorCodeEnum.USER_ACCOUNT_EXPIRED;
        }  else if (exception instanceof CredentialsExpiredException) {
            //证书过期
            errorCodeEnum = ComErrorCodeEnum.USER_CREDENTIALS_EXPIRED;
        } else if (exception instanceof DisabledException) {
            //账号不可用
            errorCodeEnum = ComErrorCodeEnum.USER_ACCOUNT_DISABLE;
        } else if (exception instanceof LockedException) {
            //账号锁定
            errorCodeEnum = ComErrorCodeEnum.USER_ACCOUNT_LOCKED;
        } else if (exception instanceof UsernameNotFoundException) {
            //用户不存在
            errorCodeEnum = ComErrorCodeEnum.USER_ACCOUNT_NOT_EXIST;
        }else if (exception instanceof BadCredentialsException) {
            //密码错误
            errorCodeEnum = ComErrorCodeEnum.USER_CREDENTIALS_ERROR;
        }else{
            //其他错误
            errorCodeEnum = ComErrorCodeEnum.UNKNOWN_ERROR;
        }
        //返回json数据
        response.setCharacterEncoding(CharsetUtil.UTF_8);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        PrintWriter printWriter = response.getWriter();
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("status","F");
        map.put("retCode", errorCodeEnum.getCode());
        map.put("retMsg", errorCodeEnum.getDesc());
        map.put("timestamp", DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
        printWriter.append(new ObjectMapper().writeValueAsString(map));
    }
}
