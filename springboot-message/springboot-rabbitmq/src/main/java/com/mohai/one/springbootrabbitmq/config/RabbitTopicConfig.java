package com.mohai.one.springbootrabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 主题交换机
 * topic策略可以根据routingKey的规则（通配符方式）进行去匹配队列进行转发规则为*.#.*
 * @Auther: moerhai@qq.com
 * @Date: 2020/10/4 14:31
 */
@Configuration
public class RabbitTopicConfig {

    public final static String TOPIC_NAME = "amqp-topic";

    @Bean
    TopicExchange topicExchange(){
        return new TopicExchange(TOPIC_NAME,true,false);
    }

    @Bean
    Queue xiaomi(){
        return new Queue("xiaomi");
    }

    @Bean
    Queue huawei(){
        return new Queue("huawei");
    }

    @Bean
    Queue phone() {
        return new Queue("phone");
    }

    @Bean
    Binding xiaomiBinding(){
        //xiaomi.#：表示消息的routingKey是以xiaomi开头的就会路由到xiaomi的队列
        return BindingBuilder.bind(xiaomi()).to(topicExchange()).with("xiaomi.#");
    }

    @Bean
    Binding huaweiBinding(){
        return BindingBuilder.bind(huawei()).to(topicExchange()).with("huawei.#");
    }

    @Bean
    Binding phoneBinding(){
        //#.phone.#：表示消息的routingKey中带phone的都会路由到phone的队列
        return BindingBuilder.bind(phone()).to(topicExchange()).with("#.phone.#");
    }

}