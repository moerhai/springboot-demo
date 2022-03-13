package com.mohai.one.rocketmqconsumer.service;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQReplyListener;
import org.springframework.stereotype.Service;

/**
 * 可以返回响应的监听器
 * @Auther: moerhai@qq.com
 * @Date: 2020/11/12 01:16
 */
@Service
@RocketMQMessageListener(
        topic = "stringRequestTopic",
        consumerGroup = "stringRequestConsumer"
)
public class MyStringConsumerReplyService implements RocketMQReplyListener<String, String> {

    @Override
    public String onMessage(String message) {
        System.out.printf("MyStringConsumerReplyService接收到消息: %s \n", message);
        return "reply string";
    }
}