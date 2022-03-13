package com.mohai.one.springbootrabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;

/**
 *
 * @Auther: moerhai@qq.com
 * @Date: 2020/10/4 13:31
 */
//@Configuration
public class RabbitMQProducerConfiguration {

    private static final String QUEUE_NAME = "";
    private static final String ACK_QUEUE_NAME = "";
    private static final String TOPIC_EXCHANGE_NAME = "";

    @Bean
    RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }


    @Bean
    Queue queue(RabbitAdmin rabbitAdmin) {
        Queue queue = new Queue(QUEUE_NAME, true);
        rabbitAdmin.declareQueue(queue);
        return queue;
    }


    @Bean
    Queue ackQueue(RabbitAdmin rabbitAdmin) {
        Queue queue = new Queue(ACK_QUEUE_NAME, true);
        rabbitAdmin.declareQueue(queue);
        return queue;
    }


    @Bean
    TopicExchange exchange(RabbitAdmin rabbitAdmin) {
        TopicExchange topicExchange = new TopicExchange(TOPIC_EXCHANGE_NAME);
        rabbitAdmin.declareExchange(topicExchange);
        return topicExchange;
    }


    @Bean
    Binding bindingExchange(Queue queue, TopicExchange exchange, RabbitAdmin rabbitAdmin) {
        Binding binding = BindingBuilder.bind(queue).to(exchange).with(QUEUE_NAME);
        rabbitAdmin.declareBinding(binding);
        return binding;
    }


    @Bean
    Binding bindingExchangeAck(Queue ackQueue, TopicExchange exchange, RabbitAdmin rabbitAdmin) {
        Binding binding = BindingBuilder.bind(ackQueue).to(exchange).with(ACK_QUEUE_NAME);
        rabbitAdmin.declareBinding(binding);
        return binding;
    }


    /**
     * 生产者用
     *
     * @return
     */
    @Bean
    public RabbitMessagingTemplate rabbitMessagingTemplate(RabbitTemplate rabbitTemplate) {
        RabbitMessagingTemplate rabbitMessagingTemplate = new RabbitMessagingTemplate();
        rabbitMessagingTemplate.setMessageConverter(jackson2Converter());
        rabbitMessagingTemplate.setRabbitTemplate(rabbitTemplate);
        return rabbitMessagingTemplate;
    }


    @Bean
    public MappingJackson2MessageConverter jackson2Converter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        return converter;
    }

}