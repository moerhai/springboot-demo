package com.mohai.one.springbootkafka.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/10/6 02:00
 */
@Service
public class KafkaMessageSendService {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaMessageSendService.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${moh.kafka.app.topic}")
    private String appTopic;

    @Value("${moh.kafka.custom.topic}")
    private String customTopic;

    /**
     * 指定主题发送消息
     * @param message
     */
    public void sendMessage(String message){
        System.out.println("sendMessage===>message="+message);
        kafkaTemplate.sendDefault(0,"body", message);
    }

    /**
     * 异步第一种写法
     * 发送消息，异步获取发送结果
     * @param message
     */
    public void sendAsync(String message){
        System.out.println("sendAsync===>topic="+appTopic+",message="+message);
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(appTopic, message);
        future.addCallback(success -> {
                    // 消息发送到的topic
                    String topic = success.getRecordMetadata().topic();
                    // 消息发送到的分区
                    int partition = success.getRecordMetadata().partition();
                    // 消息在分区内的offset
                    long offset = success.getRecordMetadata().offset();
                    System.out.println("发送消息成功:" + topic + "-" + partition + "-" + offset);
                    LOG.info("KafkaMessageSendService===>发送消息成功！");
                },
                fail -> LOG.error("KafkaMessageSendService===>发送消息失败！")
        );
    }

    /**
     * 异步第二种写法
     * 发送消息，异步获取发送结果
     * @param message
     */
    public void sendAsync2(String message){
        System.out.println("sendAsync2===>topic="+appTopic+",message="+message);
        kafkaTemplate.send(appTopic, message).addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable ex) {
                System.out.println("发送消息失败："+ex.getMessage());
            }
            @Override
            public void onSuccess(SendResult<String, String> result) {
                System.out.println("发送消息成功：" + result.getRecordMetadata().topic() + "-"
                        + result.getRecordMetadata().partition() + "-" + result.getRecordMetadata().offset());
            }
        });
    }

    /**
     * 发送消息，同步获取发送结果
     * @param message
     */
    public void sendSync(String message){
        System.out.println("sendSync===>topic="+customTopic+",message="+message);
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(customTopic, message);
        try {
            SendResult<String,String> result = future.get();
            LOG.info(result.toString() + "===>发送消息成功！");
        }catch (Throwable e){
            LOG.error("发送消息失败！");
            e.printStackTrace();
        }
    }

    /**
     * 发送消息，包含事务
     * @param mak
     */
    public void sendMessageTx(String mak){
        boolean flag = kafkaTemplate.executeInTransaction(t ->{
            // 发送第一个消息
            t.send(customTopic,"first message");
            if("error".equals(mak)){
                throw new RuntimeException("failed");
            }
            // 发送第二个消息
            t.send(customTopic,"second message");
            return true;
        });
        if(flag){
          LOG.info("===>发送消息成功！");
        }else{
          LOG.error("===>发送消息失败！");
        }
    }

}