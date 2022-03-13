package com.mohai.one.springbootdataredis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/4/4 0:05
 */
@Service
public class RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public Set index(){
       return stringRedisTemplate.keys("*");
    }

    public boolean clearKey(String key){
        return stringRedisTemplate.delete(key);
    }

    public void set(String key,String value){
        stringRedisTemplate.opsForValue().set(key,value);
    }

    public String get(String key){
        return stringRedisTemplate.opsForValue().get(key);
    }

}
