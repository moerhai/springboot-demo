package com.mohai.one.springboothikaricp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;
import java.sql.Connection;

@SpringBootApplication
public class SpringbootHikaricpApplication implements ApplicationRunner {

	@Autowired
	private DataSource dataSource;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		try(Connection conn = dataSource.getConnection()) {
			System.out.println(conn);
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringbootHikaricpApplication.class, args);
	}

}
