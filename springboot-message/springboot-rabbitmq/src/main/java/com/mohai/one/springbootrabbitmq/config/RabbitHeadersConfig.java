package com.mohai.one.springbootrabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 头部交换机
 * headers策略 根据路由规则的header进行匹配的
 * 匹配的时候需要传入一个map集合，routingkey去匹配map即可中的key value，
 * 匹配规则可以是any或者all，any表示只要包含任意信息就可以，all表示所有信息都必须匹配
 * @Auther: moerhai@qq.com
 * @Date: 2020/10/4 14:37
 */
@Configuration
public class RabbitHeadersConfig {

    public final static String HEADERS_NAME = "amqp-headers";

    @Bean
    HeadersExchange headersExchange() {
        return new HeadersExchange(HEADERS_NAME, true, false);
    }

    //分别创建两个不同header的队列
    @Bean
    Queue queueName(){
        return new Queue("name-queue");
    }
    @Bean
    Queue queueAge(){
        return new Queue("age-queue");
    }
    @Bean
    Binding bindingName(){
        Map<String,Object> map = new HashMap<>();
        map.put("name","hello");
        //表示如果routingKey匹配的map集合中的key value 就会将消息转发到对应的路由上
        return BindingBuilder.bind(queueName()).to(headersExchange()).whereAny(map).match();
    }

    @Bean
    Binding bindingAge(){
        return BindingBuilder.bind(queueAge()).to(headersExchange()).where("age").exists();
    }

}