package com.mohai.one.springbootdatajdbc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
//定义扫描包名
@EnableJdbcRepositories("com.mohai.one.springbootdatajdbc.repository")
//开启事务管理
@EnableTransactionManagement
public class DataJdbcConfig {


}
