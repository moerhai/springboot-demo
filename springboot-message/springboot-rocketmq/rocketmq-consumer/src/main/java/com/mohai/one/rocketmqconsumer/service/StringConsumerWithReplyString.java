package com.mohai.one.rocketmqconsumer.service;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQReplyListener;
import org.springframework.stereotype.Service;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/11/11 13:42
 */
//@Service
//@RocketMQMessageListener(topic = "${demo.rocketmq.stringRequestTopic}", consumerGroup = "${demo.rocketmq.stringRequestConsumer}", selectorExpression = "${demo.rocketmq.tag}")
public class StringConsumerWithReplyString implements RocketMQReplyListener<String, String> {

    @Override
    public String onMessage(String message) {
        System.out.printf("------- StringConsumerWithReplyString received: %s \n", message);
        return "reply string";
    }
}