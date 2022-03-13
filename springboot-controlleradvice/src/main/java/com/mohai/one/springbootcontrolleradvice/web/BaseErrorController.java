package com.mohai.one.springbootcontrolleradvice.web;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseErrorController implements ErrorController {
    @Override
    public String getErrorPath() {
        return "error/500";
    }

    @RequestMapping("/500")
    public String getError(){
        return "500 错误";
    }
}
