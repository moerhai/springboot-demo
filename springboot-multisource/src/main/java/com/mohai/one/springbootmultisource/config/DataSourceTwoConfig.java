package com.mohai.one.springbootmultisource.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceTwoConfig {

    @Bean("two")
    @ConfigurationProperties(prefix = "spring.datasource.two")
    public DataSource twoMysqlDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }


}
