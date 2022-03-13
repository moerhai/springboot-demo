package com.mohai.one.app.core.component;

import cn.hutool.core.util.CharsetUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/12/15 02:10
 */
@Component
public class LoginAuthSuccessHandler implements AuthenticationSuccessHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginAuthSuccessHandler.class);
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //登录成功后更新用户表上次登录时间等字段
        User userDetails = (User) authentication.getPrincipal();
        LOGGER.debug("[{}]登录成功！",userDetails.getUsername());
        // 可以处理当前用户有哪些菜单权限，返回到前台动态显示
        // TODO

        //返回json数据
        response.setCharacterEncoding(CharsetUtil.UTF_8);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        PrintWriter printWriter = response.getWriter();
        Map<String,Object> map = new HashMap();
        map.put("status", 200);
        map.put("msg", "success");
        printWriter.append(new ObjectMapper().writeValueAsString(map));
    }
}
