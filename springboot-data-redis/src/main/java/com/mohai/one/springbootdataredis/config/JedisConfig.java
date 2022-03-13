package com.mohai.one.springbootdataredis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPool;

/**
 * @Auther: moerhai@qq.com
 * @Date 2020/4/4 0:28
 */
//@Configuration
public class JedisConfig {

    @Autowired
    private JedisConnectionFactory jedisConnectionFactory;

    /**
     * 创建JedisPool
     */
    @Bean
    public JedisPool redisPool() {
        JedisPool jedisPool = new JedisPool(jedisConnectionFactory.getPoolConfig());
        return jedisPool;
    }




}