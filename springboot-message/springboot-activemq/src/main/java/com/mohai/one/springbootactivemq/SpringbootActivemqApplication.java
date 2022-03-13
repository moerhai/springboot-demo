package com.mohai.one.springbootactivemq;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.jms.Queue;

@SpringBootApplication
public class SpringbootActivemqApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootActivemqApplication.class, args);
	}

}