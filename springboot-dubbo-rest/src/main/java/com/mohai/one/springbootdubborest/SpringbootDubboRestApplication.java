package com.mohai.one.springbootdubborest;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableDubbo
public class SpringbootDubboRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootDubboRestApplication.class, args);
	}

}
