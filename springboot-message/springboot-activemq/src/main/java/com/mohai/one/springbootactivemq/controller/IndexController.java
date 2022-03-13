package com.mohai.one.springbootactivemq.controller;

import com.mohai.one.springbootactivemq.sender.ActiveMQSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @Auther: moerhai@qq.com
 * @Date: 2020/10/4 11:27
 */
@RestController
public class IndexController {

    @Autowired
    private ActiveMQSender activeMQSender;

    @RequestMapping("/queue")
    public String queue() {
        activeMQSender.sendQueue("hello,mohai!");
        return "SUCCESS";
    }

    @RequestMapping("/topic")
    public String topic() {
        activeMQSender.sendTopic("hello,mohai!");
        return "SUCCESS";
    }
}