package com.mohai.one.springbootmultisource.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DataSourceOneConfig {

    //指定同一个接口有多个实现类可以注入的时候 , 默认注入哪一个
    @Primary // 表示这个数据源是默认数据源, 这个注解必须要加，因为不加的话spring将分不清楚那个为主数据源（默认数据源）
    @Bean("one")
    @ConfigurationProperties(prefix = "spring.datasource.one")
    public DataSource oneMysqlDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }


}
