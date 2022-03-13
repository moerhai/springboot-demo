package com.mohai.one.springbootinterceptor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class SpringbootInterceptorApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootInterceptorApplication.class, args);
	}

}
