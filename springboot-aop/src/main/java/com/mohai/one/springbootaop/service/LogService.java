package com.mohai.one.springbootaop.service;

import com.mohai.one.springbootaop.annotation.Log;
import org.springframework.stereotype.Service;

@Service
public class LogService {

    @Log(name="访问serviceA")
    public void testA(){
        System.out.println("testA");
        testB();
    }

    @Log(name="访问serviceB")
    public void testB(){
        System.out.println("testB");
    }
}
