package com.mohai.one.springbootrunner;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class MyPostConstruct {

    @PostConstruct
    public void load(){
        System.out.println("=======================PostConstruct正在加载");
        throw new RuntimeException();
    }

}
