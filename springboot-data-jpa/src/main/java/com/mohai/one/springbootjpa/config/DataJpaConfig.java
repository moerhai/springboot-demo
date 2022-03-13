package com.mohai.one.springbootjpa.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
//定义扫描包名
@EnableJpaRepositories("com.mohai.one.springbootjpa.repository")
@EnableTransactionManagement    // 启用事务管理器
public class DataJpaConfig {

}
