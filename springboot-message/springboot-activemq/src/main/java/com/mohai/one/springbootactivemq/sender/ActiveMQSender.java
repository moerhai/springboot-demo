package com.mohai.one.springbootactivemq.sender;

import com.mohai.one.springbootactivemq.domain.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Queue;
import javax.jms.Topic;

/**
 *
 * @Auther: moerhai@qq.com
 * @Date: 2020/10/4 16:52
 */
@Component
public class ActiveMQSender {

    @Autowired
    JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private Queue queue;

    @Autowired
    private Topic topic;

    //生产者 queue模式
    public void sendQueue(String message) {
//        Message message = new Message();
//        message.setTitle("短信消息");
//        message.setContent(str);
//        message.setStatus((byte) 0);
        jmsMessagingTemplate.convertAndSend(queue,message);  //发消息
    }

    // 生产者 topic模式
    public void sendTopic(String message){
        jmsMessagingTemplate.convertAndSend(topic,message);  //发消息
    }

}