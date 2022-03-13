package com.mohai.one.oauth2ssoserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/10/5 10:44
 */
@Controller
public class LoginController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    /**
     * 登陆逻辑,返回的是令牌
     * @param request
     * @param response
     * @param userName
     * @param password
     * @param originalUrl
     * @return
     */
    @RequestMapping(value="/doLogin",method= RequestMethod.POST)
    public String login(HttpServletRequest request, HttpServletResponse response,
                        String userName, String password, String originalUrl) {
//        if(authSessionService.verify(userName,password)) {
//            String token = authSessionService.cacheSession(userName);
//            if(tokenTrans(request,originalUrl,userName,token)) {
//                //跳转到提示成功的页面
//                request.setAttribute("helloName", userName);
//                if(originalUrl!=null) {
//                    if(originalUrl.contains("?")) {
//                        originalUrl = originalUrl + "&ssoUser="+userName;
//                    }else {
//                        originalUrl = originalUrl + "?ssoUser="+userName;
//                    }
//                    request.setAttribute("originalUrl", originalUrl);
//                }
//            }
//            return "hello";//TO-DO 三秒跳转
//        }
        //验证不通过，重新来吧
        if(originalUrl!=null) {
            request.setAttribute("originalUrl", originalUrl);
        }
        return "login";
    }

    private boolean tokenTrans(HttpServletRequest request, String originalUrl,String userName, String token) {
        String[] paths = originalUrl.split("/");
        String shortAppServerUrl = paths[2];
        String returnUrl = "http://"+shortAppServerUrl+"/receiveToken?ssoToken="+token+"&userName="+userName;
        //http://peer1:8088/receiveToken?ssoToken=80414bcb-a71d-48c8-bfee-098a303324d4&userName=xixi
        return "success".equals(restTemplate.getForObject(returnUrl, String.class));

    }


    //校验token并注册地址
    @RequestMapping(value="/varifyToken",method=RequestMethod.GET)
    @ResponseBody
    public String varifyToken(String token, String address) {
//        return String.valueOf(authSessionService.checkAndAddAddress(token, address));
        return "";
    }

}