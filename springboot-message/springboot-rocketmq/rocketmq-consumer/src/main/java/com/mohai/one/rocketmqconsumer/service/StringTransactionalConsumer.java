package com.mohai.one.rocketmqconsumer.service;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/11/11 13:28
 */
//@Service
//@RocketMQMessageListener(topic = "${demo.rocketmq.transTopic}", consumerGroup = "string_trans_consumer")
public class StringTransactionalConsumer implements RocketMQListener<String> {
    @Override
    public void onMessage(String message) {
        System.out.printf("------- StringTransactionalConsumer received: %s \n", message);
    }
}
