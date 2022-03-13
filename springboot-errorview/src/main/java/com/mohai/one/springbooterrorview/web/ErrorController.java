package com.mohai.one.springbooterrorview.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController {

    /**
     * 404 error
     */
    @RequestMapping("/400")
    public String error400() {
        return "400";
    }

    /**
     * 500 error
     */
    @RequestMapping("/500")
    public String error500() {
        return "500";
    }

}
