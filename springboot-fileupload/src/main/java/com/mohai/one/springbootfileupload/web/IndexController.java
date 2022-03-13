package com.mohai.one.springbootfileupload.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/upload")
    public String upload() {
        return "upload";
    }

}
