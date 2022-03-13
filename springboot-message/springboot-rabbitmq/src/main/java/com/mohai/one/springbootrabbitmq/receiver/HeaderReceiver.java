package com.mohai.one.springbootrabbitmq.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/10/4 14:40
 */
@Component
public class HeaderReceiver {

    @RabbitListener(queues = "name-queue")
    public void handlerName(byte[] msg){
        System.out.println("HeaderReceiver:handlerName>>>>"+new String(msg,0,msg.length));
    }
    @RabbitListener(queues = "age-queue")
    public void handlerAge(byte[] msg){
        System.out.println("HeaderReceiver:handlerAge>>>>"+new String(msg,0,msg.length));
    }

}
