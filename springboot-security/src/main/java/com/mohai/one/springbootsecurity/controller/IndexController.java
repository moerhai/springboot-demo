package com.mohai.one.springbootsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/9/9 07:12
 */
@RestController
@RequestMapping("/index")
public class IndexController {

    @GetMapping("/")
    public String index(){
         return "hello world!";
    }

    @GetMapping("/q")
    public String home(){
        int n = 1/0;
        return "SUCCESS";
    }


    @GetMapping("/n")
    public String userRole(){
        return "SUCCESS";
    }

}
