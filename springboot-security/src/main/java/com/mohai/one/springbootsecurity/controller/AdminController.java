package com.mohai.one.springbootsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/10/2 00:12
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/n")
    public String queryAdmin(){
        return "SUCCESS";
    }

}
