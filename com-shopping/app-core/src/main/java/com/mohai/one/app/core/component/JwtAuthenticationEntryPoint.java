package com.mohai.one.app.core.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 当未登录或者token失效的用户在访问无权限资源时，抛出异常处理返回401
 *
 * @Auther: moerhai@qq.com
 * @Date: 2020/11/25 00:58
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        LOGGER.debug("‘{}’ 请求未授权",request.getRequestURL());
        // 当用户在没有任何凭据的情况下尝试访问受保护的REST资源时，将调用此方法发送401响应。
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"暂未登录或token已过期");
    }
}