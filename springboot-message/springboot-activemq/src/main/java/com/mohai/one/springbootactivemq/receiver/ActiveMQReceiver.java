package com.mohai.one.springbootactivemq.receiver;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;

/**
 * 消息消费者
 * @Auther: moerhai@qq.com
 * @Date: 2020/10/4 16:50
 */
@Component
public class ActiveMQReceiver {

    // queue模式的消费者，监听mohai.queue队列
    @JmsListener(destination="${spring.activemq.queue-name}", containerFactory="queueListener")
    public void readActiveQueue(TextMessage message) throws JMSException {
        System.out.println("ActiveMq.Queue接收消息>>>"+message.getText());
    }

    // topic模式的消费者，监听mohai.topic主题
    @JmsListener(destination="${spring.activemq.topic-name}", containerFactory="topicListener")
    public void receiveActiveTopic(TextMessage message) throws JMSException {
        System.out.println("ActiveMq.Topic接收消息>>>" + message.getText());
    }

}