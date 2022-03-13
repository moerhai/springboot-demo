package com.mohai.one.firstspringboot;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 */
@RestController
public class HelloWorldController {

    @RequestMapping("/hello")
    public String test(){
        return "hello world!";
    }
}
