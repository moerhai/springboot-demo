package com.mohai.one.rocketmqconsumer.service;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/11/12 02:35
 */
@Service
@RocketMQMessageListener(topic = "spring-transaction-topic", consumerGroup = "string_trans_consumer")
public class MyTransactionalConsumerService implements RocketMQListener<String> {
    @Override
    public void onMessage(String message) {
        System.out.printf("MyTransactionalConsumerService接收到: %s \n", message);
    }
}
