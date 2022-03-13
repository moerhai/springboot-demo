package com.mohai.one.springbootmultisource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import javax.sql.DataSource;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class SpringbootMultisourceApplication implements ApplicationRunner {

	@Autowired
	//@Qualifier("one")
	private DataSource one;

	@Autowired
	//@Qualifier("two")
	private DataSource two;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		System.out.println(one.getConnection().getCatalog());
		System.out.println(two.getConnection().getCatalog());
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringbootMultisourceApplication.class, args);
	}

}
