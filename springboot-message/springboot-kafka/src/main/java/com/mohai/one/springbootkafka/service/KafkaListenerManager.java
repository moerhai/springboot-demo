package com.mohai.one.springbootkafka.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.stereotype.Service;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/11/12 22:34
 */
@Service
public class KafkaListenerManager {

    @Autowired
    private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

    /**
     * 开启监听
     * @param id 指向@KafkaListener的id值
     */
    public void start(String id){
        kafkaListenerEndpointRegistry.getListenerContainer(id).start();
    }

    /**
     * 停止监听
     * @param id 指向@KafkaListener的id值
     */
    public void stop(String id){
        kafkaListenerEndpointRegistry.getListenerContainer(id).pause();
    }

    /**
     * 继续监听
     * @param id 指向@KafkaListener的id值
     */
    public void resume(String id){
        kafkaListenerEndpointRegistry.getListenerContainer(id).resume();
    }

}