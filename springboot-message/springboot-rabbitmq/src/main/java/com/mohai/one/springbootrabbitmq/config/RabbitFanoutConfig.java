package com.mohai.one.springbootrabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 广播式交换机
 * Fanout策略（只要是与它绑定的队列，都会收到消息与routingKey无关）
 * @Auther: moerhai@qq.com
 * @Date: 2020/10/4 13:13
 */
@Configuration
public class RabbitFanoutConfig {

    public final static String FANOUT_NAME ="amqp-fanout";

    @Bean
    FanoutExchange fanoutExchange(){
        return new FanoutExchange(FANOUT_NAME,true,false);
    }

    @Bean
    Queue queueOne(){
        return new Queue("queue-one");
    }

    @Bean
    Queue queueTwo(){
        return new Queue("queue-two");
    }

    @Bean
    Binding bindingOne(){
        return BindingBuilder.bind(queueOne()).to(fanoutExchange());
    }

    @Bean
    Binding bindingTwo(){
        return BindingBuilder.bind(queueTwo()).to(fanoutExchange());
    }


}