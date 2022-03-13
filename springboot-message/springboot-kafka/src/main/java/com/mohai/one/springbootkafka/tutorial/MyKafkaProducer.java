package com.mohai.one.springbootkafka.tutorial;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.errors.AuthorizationException;
import org.apache.kafka.common.errors.OutOfOrderSequenceException;
import org.apache.kafka.common.errors.ProducerFencedException;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.KafkaException;

import java.util.Properties;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/11/13 22:35
 */
public class MyKafkaProducer {

    public static void main(String[]args){
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        //设置事务id
        props.put("transactional.id", "my-transactional-id");
        KafkaProducer producer = new KafkaProducer(props, new StringSerializer(), new StringSerializer());
        //
        producer.initTransactions();
        try {
            //开启事务
            producer.beginTransaction();
            for (int i = 0; i < 100; i++)
                producer.send(new ProducerRecord("my-topic", Integer.toString(i), Integer.toString(i)));
            //提交事务
            producer.commitTransaction();
            //如果遭遇致命异常，就关闭生产者
        } catch (ProducerFencedException | OutOfOrderSequenceException | AuthorizationException e) {
            // We can't recover from these exceptions, so our only option is to close the producer and exit.
            producer.close();
            //中断事务
            //如果某个producer.send()或事务调用在事务期间遇到不可恢复的错误，就会抛出KafkaException
        } catch (KafkaException e) {
            // For all other exceptions, just abort the transaction and try again.
            producer.abortTransaction();
        }
        producer.close();
    }
}