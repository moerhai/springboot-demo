package com.mohai.one.rocketmqconsumer.service;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/11/11 13:28
 */
//@Service
//@RocketMQMessageListener(topic = "${demo.rocketmq.topic}", consumerGroup = "string_consumer", selectorExpression = "${demo.rocketmq.tag}")
public class StringConsumer implements RocketMQListener<String> {
    @Override
    public void onMessage(String message) {
        System.out.printf("------- StringConsumer received: %s \n", message);
    }
}
