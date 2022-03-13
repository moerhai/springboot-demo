package com.mohai.one.springbooterrorview.web;

import com.mohai.one.springbooterrorview.exception.MyException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @RequestMapping("/index")
    public String index(){
        int n = 1/0;
        return "SUCCESS";
    }

    @RequestMapping("/test")
    public String testException(){
        throw new MyException("sorry,it is too bad");
    }

}
