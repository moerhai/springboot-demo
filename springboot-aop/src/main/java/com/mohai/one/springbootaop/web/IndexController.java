package com.mohai.one.springbootaop.web;

import com.mohai.one.springbootaop.annotation.Log;
import com.mohai.one.springbootaop.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @Autowired
    private LogService logService;

    @Log(name = "访问index接口")
    @RequestMapping("/index")
    public String index(){
        System.out.println("执行index");
        return "welcome to my site";
    }

    @RequestMapping("/index2")
    public String index2(){
        logService.testA();
        return "SUCCESS";
    }

}
