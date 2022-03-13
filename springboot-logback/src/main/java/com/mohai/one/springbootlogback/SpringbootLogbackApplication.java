package com.mohai.one.springbootlogback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootLogbackApplication {

    public static void main(String[] args) {
        try{
            SpringApplication.run(SpringbootLogbackApplication.class, args);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
