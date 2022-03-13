package com.mohai.one.springbootrabbitmq.controller;

import com.mohai.one.springbootrabbitmq.sender.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/10/4 11:34
 */
@RestController
public class IndexController {

    @Autowired
    MessageSender messageSender;

    @RequestMapping("/index")
    public String index(){
        messageSender.send("hello world!");
        return "SUCCESS";
    }

}