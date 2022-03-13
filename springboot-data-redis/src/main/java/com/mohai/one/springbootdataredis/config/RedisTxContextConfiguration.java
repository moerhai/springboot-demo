package com.mohai.one.springbootdataredis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/7/10 01:33
 */
@Configuration
//启用声明式事务管理，可将事务由Spring管理
//@EnableTransactionManagement
public class RedisTxContextConfiguration {

//    @Bean
//    public PlatformTransactionManager transactionManager() throws SQLException {
//        return new DataSourceTransactionManager(dataSource());
//    }
//
//    @Bean
//    public DataSource dataSource() throws SQLException {
//        //...需要配合使用 JDBC，暂不演示
//    }

    @Autowired
    private JedisConnectionFactory jedisConnectionFactory;

    @Bean
    public StringRedisTemplate redisTemplate() {
        StringRedisTemplate template = new StringRedisTemplate(jedisConnectionFactory);
        // 显式启用事务支持
        template.setEnableTransactionSupport(true);
        return template;
    }


}
