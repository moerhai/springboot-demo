package com.mohai.one.springbootapplication.config;

import com.mohai.one.springbootapplication.bean.User;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootConfiguration(proxyBeanMethods = false)
public class MyTestConfiguration {

    public MyTestConfiguration() {
        System.out.println("测试配置类！");
    }

    @Bean
    public User getUser(){
        User user = new User();
        user.setName("Tom");
        user.setAge(10);
        return user;
    }

    @Bean
    public static User getUser2(){
        User user = new User();
        user.setName("Jack");
        user.setAge(20);
        return user;
    }


}
