package com.mohai.one.springbootlog4j2.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @GetMapping("/index")
    public String getIndex() {
        logger.info("logback整合成功了");
        logger.error("logback整合成功了");
        return "SUCCESS";
    }
}