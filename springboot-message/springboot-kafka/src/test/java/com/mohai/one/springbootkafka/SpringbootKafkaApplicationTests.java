package com.mohai.one.springbootkafka;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;

import java.io.IOException;

/**
 * 通过junit测试用例
 * 启动一个Kafka Server服务，包含四个Broker节点
 */
@SpringBootTest
/**
 * value：broker节点数量
 * count：同value作用一样，互为别名
 * ports：指定多个端口
 * controlledShutdown：控制关闭开关，
 * 主要用来在Broker意外关闭时减少此Broker上Partition的不可用时间
 */
@EmbeddedKafka(count = 4,ports = {9092,9093,9094,9095})
class SpringbootKafkaApplicationTests {

	@Autowired
	KafkaTemplate kafkaTemplate;

	@Autowired
	EmbeddedKafkaBroker broker;

	@Test
	void contextLoads() throws IOException {
		System.out.println(broker.getBrokersAsString());
		System.out.println(kafkaTemplate);
		// 测试方法不支持控制台输入，会导致一直阻塞
		System.in.read();
	}

}