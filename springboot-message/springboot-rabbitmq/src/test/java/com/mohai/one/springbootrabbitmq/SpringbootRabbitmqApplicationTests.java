package com.mohai.one.springbootrabbitmq;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootRabbitmqApplicationTests {

	@Autowired
	RabbitTemplate rabbitTemplate;

	@Test
	void contextLoads() {
		//两个参数第一个是routingKey、第二个为消息内容
		rabbitTemplate.convertAndSend("hello.RabbitMQ","hello RabbitMQ test");
	}


	@Test
	void rabbitFanout(){
		//三个参数表示RabbitFanoutConfig的名称、routingkey、消息内容
		rabbitTemplate.convertAndSend("amqp-fanout",null,"hello fanout test");
	}


	@Test
	void rabbitTopic(){
		//根据匹配规则该消息只能被xiaomi的队列收到
		rabbitTemplate.convertAndSend("amqp-topic","xiaomi.news","小米新闻");
		//根据匹配规则该消息只能被phone的队列收到
		rabbitTemplate.convertAndSend("amqp-topic","vivo.phone","vivo手机");
		//根据匹配规则该消息可以别huawei和phone两个队列收到
		rabbitTemplate.convertAndSend("amqp-topic","huawei.phone","华为手机");

	}



	@Test
	public void rabbitHeader(){
		//设置消息，并且设置header，setHeader("name","hello")分别表示map集合中的key、value
		Message nameMessage =
				MessageBuilder.withBody("hello name".getBytes()).setHeader("name","hello").build();
		Message ageMessage =
				MessageBuilder.withBody("hello 99 age".getBytes()).setHeader("age","99").build();
		rabbitTemplate.send("amqp-headers",null,nameMessage);
		rabbitTemplate.send("amqp-headers",null,ageMessage);
	}

}
