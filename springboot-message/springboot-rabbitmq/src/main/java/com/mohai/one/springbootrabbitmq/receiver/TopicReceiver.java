package com.mohai.one.springbootrabbitmq.receiver;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 *
 * @Auther: moerhai@qq.com
 * @Date: 2020/10/4 14:36
 */
@Component
public class TopicReceiver {

    //分别监听名称为xiaomi、huawei、phone的队列
    @RabbitListener(queues = "xiaomi")
    public void handlerXM(String msg){
        System.out.println("TopicReceiver:handlerXM>>>"+msg);
    }
    @RabbitListener(queues = "huawei")
    public void handlerHW(String msg){
        System.out.println("TopicReceiver:handlerHW>>>"+msg);
    }
    @RabbitListener(queues = "phone")
    public void handlerPHONE(String msg){
        System.out.println("TopicReceiver:handlerPHONE>>>"+msg);
    }

    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(value = "topic.exchange",durable = "true",type = "topic"),
            value = @Queue(value = "consumer_queue",durable = "true"),
            key = "key.#"
    ))
    public void processMessage(Message message) {
        System.out.println(message);
    }

}
