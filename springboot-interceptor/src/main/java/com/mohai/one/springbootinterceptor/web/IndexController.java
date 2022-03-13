package com.mohai.one.springbootinterceptor.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @RequestMapping("/index")
    public String index(){
        System.out.println("执行index");
        return "welcome to my site";
    }

}
