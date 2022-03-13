package com.mohai.one.rocketmqconsumer.service;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.UtilAll;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.springframework.stereotype.Service;

/**
 * 指定tag的消费者
 * 注解中指定主题，选择表达式，消费组
 * 采用push方式获取消息
 *
 * @Auther: moerhai@qq.com
 * @Date: 2020/11/12 00:35
 */
@Service
@RocketMQMessageListener(
        topic = "${mohai.rocketmq.msgExtTopic}",
        selectorExpression = "tag0||tag1",
        consumerGroup = "${spring.application.name}-message-ext-consumer"
)
public class MyMessageExtConsumerService implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {

    // 实现消息的消费处理
    @Override
    public void onMessage(MessageExt message) {
        System.out.printf("MyMessageExtConsumerService获取到消息, 消息Id: %s, 消息体:%s \n", message.getMsgId(), new String(message.getBody()));
    }

    // 设置从当前时间点开始消费消息
    @Override
    public void prepareStart(DefaultMQPushConsumer consumer) {
        // set consumer consume message from now
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_TIMESTAMP);
        consumer.setConsumeTimestamp(UtilAll.timeMillisToHumanString3(System.currentTimeMillis()));
    }
}
