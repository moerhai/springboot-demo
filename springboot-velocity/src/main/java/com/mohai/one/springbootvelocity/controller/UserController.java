package com.mohai.one.springbootvelocity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class UserController {

    @RequestMapping("/")
    public String velocityTest(Map map){
        map.put("message", "这是测试的内容。。。");
        map.put("toUserName", "逍遥子");
        map.put("fromUserName", "行颠");
        return "index";
    }

}
