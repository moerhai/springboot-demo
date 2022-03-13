package com.mohai.one.springbootrocketmq.producer;

import com.alibaba.fastjson.JSON;
import com.mohai.one.springbootrocketmq.domain.User;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/10/6 01:52
 */
public class Producer {

    public static void main(String[] args) throws MQClientException {
        DefaultMQProducer producer = new DefaultMQProducer("test-group");
        producer.setNamesrvAddr("localhost:9876");
        producer.setInstanceName("rmq-instance");
        producer.start();
        try {
            for (int i=0;i<100;i++){
                User user = new User();
                user.setLoginName("abc"+i);
                user.setPwd(String.valueOf(i));
                Message message = new Message("log-topic", "user-tag",JSON.toJSONString(user).getBytes());
                System.out.println("生产者发送消息:"+ JSON.toJSONString(user));
                producer.send(message);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        producer.shutdown();
    }
}