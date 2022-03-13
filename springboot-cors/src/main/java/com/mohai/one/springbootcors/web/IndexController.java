package com.mohai.one.springbootcors.web;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @CrossOrigin
    @RequestMapping("/index")
    public String index(){
        return "hello man";
    }

}
