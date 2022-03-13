package com.mohai.one.springbootconfiguration;

import com.mohai.one.springbootconfiguration.bean.House;
import com.mohai.one.springbootconfiguration.bean.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

//@Configuration(proxyBeanMethods = true)
@Component
public class MyConfiguration {

    @Bean
    public User getUser(){
        User user = new User();
        user.setHouse(getHouse());
        return user;
    }

    @Bean
    public House getHouse(){
        return new House();
    }

}
