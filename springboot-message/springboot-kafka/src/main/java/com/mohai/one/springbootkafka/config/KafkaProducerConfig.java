package com.mohai.one.springbootkafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.transaction.KafkaTransactionManager;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/11/12 13:19
 */
@Configuration
@EnableKafka
public class KafkaProducerConfig {

    @Value("${spring.kafka.producer.bootstrap-servers}")
    private String servers;
    @Value("${spring.kafka.producer.acks}")
    private String acks;
    @Value("${spring.kafka.producer.retries}")
    private int retries;
    @Value("${spring.kafka.producer.compression-type}")
    private String compressionType;
    @Value("${spring.kafka.producer.linger}")
    private int linger;
    @Value("${spring.kafka.producer.batch-size}")
    private int batchSize;
    @Value("${spring.kafka.producer.buffer-memory}")
    private int bufferMemory;
    @Value("${spring.kafka.producer.key-serializer}")
    private String keySerializer;
    @Value("${spring.kafka.producer.value-serializer}")
    private String valueSerializer;

    @Value("${spring.kafka.template.default-topic}")
    private String defaultTopic;

    @Value("${moh.kafka.app.topic}")
    private String appTopic;

    @Value("${moh.kafka.custom.topic}")
    private String customTopic;

    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        props.put(ProducerConfig.RETRIES_CONFIG, retries);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, batchSize);
        props.put(ProducerConfig.ACKS_CONFIG, acks);
        props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, compressionType);
        props.put(ProducerConfig.LINGER_MS_CONFIG, linger);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, bufferMemory);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializer);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer);
        return props;
    }

    public ProducerFactory<String, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    /**
     * 发送消息模板 KafkaTemplate
     * @return
     */
    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        DefaultKafkaProducerFactory producerFactory= (DefaultKafkaProducerFactory)producerFactory();
        // 如果需要开启事务的话把下面两行注掉的放开
         producerFactory.transactionCapable();
         producerFactory.setTransactionIdPrefix("tran-");
        KafkaTemplate kafkaTemplate = new KafkaTemplate<String, String>(producerFactory);
        kafkaTemplate.setDefaultTopic(defaultTopic);
        return kafkaTemplate;
    }


    /**
     * 获取生产者工厂
     */
//    @Bean(name="newKafkaProducerFactory")
//    public ProducerFactory<Object, Object> newProducerFactory() {
//        Map<String, Object> producerProperties = kafkaProperties.buildProducerProperties();
//        // 修改参数名称
//        producerProperties.put(ProducerConfig.ACKS_CONFIG,"all");
//        DefaultKafkaProducerFactory<Object, Object> factory = new DefaultKafkaProducerFactory<>(
//                producerProperties);
//        String transactionIdPrefix = kafkaProperties.getProducer().getTransactionIdPrefix();
//        if (transactionIdPrefix != null) {
//            factory.setTransactionIdPrefix(transactionIdPrefix);
//        }
//
//        return factory;
//    }
//
//    @Bean(name="newKafkaTemplate")
//    public KafkaTemplate<?, ?> newKafkaTemplate(@Qualifier("newKafkaProducerFactory") ProducerFactory<Object, Object> kafkaProducerFactory,
//                                                ProducerListener<Object, Object> kafkaProducerListener,
//                                                ObjectProvider<RecordMessageConverter> messageConverter) {
//        KafkaTemplate<Object, Object> kafkaTemplate = new KafkaTemplate<>(kafkaProducerFactory);
//        messageConverter.ifUnique(kafkaTemplate::setMessageConverter);
//        kafkaTemplate.setProducerListener(kafkaProducerListener);
//        kafkaTemplate.setDefaultTopic(kafkaProperties.getTemplate().getDefaultTopic());
//        return kafkaTemplate;
//    }





//    /**
//     * 转发送消息模板 ReplyingKafkaTemplate
//     * @return
//     */
//    @Bean
//    public ReplyingKafkaTemplate<String, String, String> replyingTemplate() {
//        return new ReplyingKafkaTemplate(producerFactory(), repliesContainer());
//    }
//
//    public ConcurrentMessageListenerContainer<String, String> repliesContainer() {
//        ConcurrentKafkaListenerContainerFactory<String, String> containerFactory = new ConcurrentKafkaListenerContainerFactory<>();
//        ConcurrentMessageListenerContainer<String, String> repliesContainer =
//                containerFactory.createContainer("replies");
//        repliesContainer.getContainerProperties().setGroupId("repliesGroup");
//        repliesContainer.setAutoStartup(false);
//        return repliesContainer;
//    }


    @Bean
    public NewTopic kRequests() {
        //主题名，分区数量，副本因子
        return new NewTopic(appTopic, 10, (short) 2);
    }

    @Bean
    public NewTopic kReplies() {
        //主题名，分区数量，副本因子
        return new NewTopic(customTopic, 10, (short) 2);
    }

}