package com.mohai.one.app.starter.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/11/20 20:49
 */
@Configuration
@MapperScan(value = {"com.mohai.one.**.dao"})
@EnableTransactionManagement
public class MapperConfiguration {
}
