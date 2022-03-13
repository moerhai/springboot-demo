package com.mohai.one.rocketmqproduce.controller;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/11/11 07:40
 */
@RestController
public class IndexController {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;


    @RequestMapping("/send")
    public String send(){
        rocketMQTemplate.convertAndSend("test-topic-1", "Hello, World!");
        rocketMQTemplate.send("test-topic-1", MessageBuilder.withPayload("Hello, World! I'm from spring message").build());
        return "SUCCESS";
    }

}