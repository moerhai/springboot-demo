package com.mohai.one.springbootrabbitmq.sender;

import com.mohai.one.springbootrabbitmq.config.RabbitDirectConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * 消息生产者 发送消息
 * @Auther: moerhai@qq.com
 * @Date: 2020/10/4 11:57
 */
@Component
public class MessageSender {

    @Autowired
    RabbitTemplate rabbitTemplate;

    /**
     * 发送消息
     * @param info
     */
    public void send(String info){
        System.out.println("发送消息>>>"+info);
        MessageProperties messageProperties = new MessageProperties();
        // 构建消息属性，设置消息id
        messageProperties.setMessageId(UUID.randomUUID().toString());
        // 构建消息，设置消息内容
        Message message = new Message(info.getBytes(),messageProperties);
        rabbitTemplate.convertAndSend(RabbitDirectConfig.DIRECT_NAME,RabbitDirectConfig.ROUTING_KEY,message);
    }

}