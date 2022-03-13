package com.mohai.one.springbootsecurityoauth.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @Auther: moerhai@qq.com
 * @Date: 2020/9/21 00:36
 */
@RestController
public class IndexController {

    @GetMapping("/admin/n")
    public String admin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getDetails());
        return "hello admin";
    }

    @GetMapping("/user/n")
    public String user() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getDetails());
        return "hello user";
    }

}