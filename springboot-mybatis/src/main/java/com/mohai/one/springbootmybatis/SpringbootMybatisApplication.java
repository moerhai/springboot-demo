package com.mohai.one.springbootmybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//如果使用了@Mapper注解，则不需要在定义扫描包了
//@MapperScan(basePackages = {"com.mohai.one.springbootmybatis.mapper"})
public class SpringbootMybatisApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootMybatisApplication.class, args);
	}

}
