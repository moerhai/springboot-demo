package com.mohai.one.springbootrabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 配置默认交换器（direct）实现
 * Direct策略（只转发给routingKey相匹配的用户）
 * @Auther: moerhai@qq.com
 * @Date: 2020/10/4 11:30
 */
@Configuration
public class RabbitDirectConfig {

    public final static String DIRECT_NAME = "amqp-direct";

    public final static String QUEUE_NAME = "my-queue";

    public final static String ROUTING_KEY = "my-routing";

    /**
     * 创建DirectExchange对象
     * @return
     */
    @Bean
    DirectExchange directExchange (){
        // 第一个参数是交换器名称、
        // 第二个参数是否持久化，重启后是否依然有效、
        // 第三个参数是长期未使用时是否自动删除
        return new DirectExchange (DIRECT_NAME,false,false);
    }

    /**
     * 提供一个消息队列Queue
     * @return
     */
    @Bean
    Queue queue(){
        // 第一个参数name值为队列名称，routingKey会与它进行匹配
        // 第二个参数durable是否持久化
        return new Queue(QUEUE_NAME,false);
    }

    /**
     * 创建一个Binding对象将Exchange和Queue绑定再一起
     * @return
     */
    @Bean
    Binding binding(){
        // 将队列queue和DirectExchange绑定在一起
        return BindingBuilder.bind(queue()).to(directExchange()).with(ROUTING_KEY);
    }

}