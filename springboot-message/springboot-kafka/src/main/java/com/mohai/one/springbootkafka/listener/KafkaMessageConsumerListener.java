package com.mohai.one.springbootkafka.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.record.TimestampType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Kafka消费者监听器
 * @Auther: moerhai@qq.com
 * @Date: 2020/11/8 23:50
 */
@Component
public class KafkaMessageConsumerListener {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaMessageConsumerListener.class);

    /**
     * 消息的元数据可以从消息头获得
     * @param foo
     * @param key
     * @param partition
     * @param topic
     * @param ts
     */
    @KafkaListener(id = "qux", topicPattern = "defaultTopic")
    public void listen(@Payload String foo,
                       @Header(KafkaHeaders.OFFSET) int offset,
                       @Header(KafkaHeaders.TIMESTAMP_TYPE) TimestampType timestampType,
                       @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key,
                       @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
                       @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                       @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long ts
    ) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("@Payload==>").append(foo).append(" ");
        stringBuilder.append("OFFSET==>").append(offset).append(" ");
        stringBuilder.append("TIMESTAMP_TYPE==>").append(timestampType).append(" ");
        stringBuilder.append("MessageKey==>").append(key).append(" ");
        stringBuilder.append("PartitionId==>").append(partition).append(" ");
        stringBuilder.append("Topic==>").append(topic).append(" ");
        stringBuilder.append("receivedTimestamp==>").append(ts);
        System.out.println(stringBuilder.toString());
    }

    /**
     * 监听接收消息
     * 指定消费哪些Topic和分区的消息
     * topicPartitions属性监听不同的partition分区
     * 设置每个Topic以及分区初始化的偏移量
     * 设置消费线程并发
     * 设置消息异常处理器
     * @param records
     */
    @KafkaListener(id = "testGroup", topics = "${moh.kafka.app.topic}",
            topicPartitions = {
                @TopicPartition(topic = "${moh.kafka.app.topic}", partitions = { "0", "1" })
            },
            concurrency = "4",errorHandler = "kafkaErrorHandler")
    public void listener(List<ConsumerRecord<?, ?>> records) {
        System.out.println(">>>批量消费一次，records.size ="+records.size());
        for (ConsumerRecord<?, ?> record : records) {
            LOG.info("topic-{}, offset-{}, value-{}", record.topic(), record.offset(), record.value());
            //判断是否为null
            Optional<?> kafkaMessage = Optional.ofNullable(record.value());
            // 判断是否不为null
            if(kafkaMessage.isPresent()){
                //获取Optional实例中的值
                Object message = kafkaMessage.get();
                String topic = record.topic();
                LOG.info("listener===>{}--message value: {}" ,topic, message);
                System.out.println("The myGroup 接收到消息>>>"+ message);
            }
        }
    }

    /**
     * 可以在partitions或partitionOffsets属性中指定每个分区，但不能同时指定这两个分区。
     *
     * @param message
     * @return
     */
    @KafkaListener(id = "testGroupString", topicPartitions = {
            @TopicPartition(topic = "${moh.kafka.custom.topic}", partitions = "0",
                    partitionOffsets = @PartitionOffset(partition = "1", initialOffset = "100"))
    },concurrency = "4",errorHandler = "kafkaErrorHandler")
    public String listenerString(String message) {
        LOG.info("listenerString===>message value: {}" , message);
        return "SUCCESS";
    }


//    /**
//     * 从版本2.0开始，消息转发，将结果转发到@SendTo指定的主题
//     * @param record
//     * @return
//     */
//    @KafkaListener(topics = "${spring.kafka.template.default-topic}")
//    @SendTo("${moh.kafka.custom.topic}")
//    public String onMessageToOther(ConsumerRecord<?, ?> record) {
//        StringBuilder sb = new StringBuilder();
//        sb.append("topic=").append(record.topic());
//        sb.append("offset=").append(record.offset());
//        sb.append("value=").append(record.value());
//        System.out.println("接收到的消息："+sb.toString());
//        return record.value()+"-forward message";
//    }

    /**
     * 手动Ack模式，使用手动确认模式时，还可以向监听器提供确认。
     * 由业务逻辑控制提交偏移量
     * 开启手动首先需要关闭自动提交，然后设置下consumer的消费模式：
     * spring.kafka.consumer.enable-auto-commit=false
     * spring.kafka.listener.ack-mode=manual
     * @param message
     * @param ack
     */
//    @KafkaListener(id = "myGroupAck", topics = "${moh.kafka.custom.topic}",
//            containerFactory = "kafkaManualAckListenerContainerFactory")
//    public void listenerAck(String message, Acknowledgment ack) {
//        LOG.info("message value: {}" , message);
//        if ("kl".equals(message)) {
//            // 提交了偏移量
//            ack.acknowledge();
//        }
//    }

    /**
     * 死信队列的Topic的规则是；业务Topic名字 + “.DLT”
     * @param message
     */
//    @KafkaListener(id = "dltGroup", topics = "${moh.kafka.custom.topic}" + ".DLT")
//    public void dltListen(String message) {
//        LOG.info("Received from DLT: " + message);
//    }


}