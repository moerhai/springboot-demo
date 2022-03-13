package com.mohai.one.rocketmqconsumer.service;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

/**
 * 普通的消费者
 * 对于topic相同，如果selectorExpression默认值是*，
 * 所以同一个topic的消费者会去获取并消费带tag的消息。
 * 如果指定了tag，则必须由包含该tag的监听器处理。
 * @Auther: moerhai@qq.com
 * @Date: 2020/11/11 01:01
 */
@Service
@RocketMQMessageListener(
        topic = "${mohai.rocketmq.topic}",
        consumerGroup = "my-consumer_test-topic"
       // selectorExpression = "*"
)
public class MyStringTagConsumerService implements RocketMQListener<String> {
    @Override
    public void onMessage(String message) {
        System.out.println("MyStringTagConsumerService接收到消息===> "+message);
    }
}