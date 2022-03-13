package com.mohai.one.springbootrabbitmq.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 配置消费者
 * 两个消费者分别监听两个不同的队列
 * @Auther: moerhai@qq.com
 * @Date: 2020/10/4 14:29
 */
@Component
public class FanoutReceiver {

    @RabbitListener(queues = "queue-one")
    public void handler1(String msg){
        System.out.println("FanoutReceiver:hanlder1>>>"+msg);
    }

    @RabbitListener(queues = "queue-two")
    public void handler2(String msg){
        System.out.println("FanoutReceiver:hanlder2>>>"+msg);
    }

}
