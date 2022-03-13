package com.mohai.one.springbootkafka.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.listener.KafkaListenerErrorHandler;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/10/19 01:02
 */
@Component("kafkaErrorHandler")
public class MyKafkaListenerErrorHandler implements KafkaListenerErrorHandler {

    private static final Logger LOG = LoggerFactory.getLogger(MyKafkaListenerErrorHandler.class);

    @Override
    public Object handleError(Message<?> message, ListenerExecutionFailedException exception) {
        System.out.println("处理异常信息："+exception);
        LOG.info("kafkaErrorHandler处理错误===>{}",message.getPayload().toString());
        return message;
    }
}