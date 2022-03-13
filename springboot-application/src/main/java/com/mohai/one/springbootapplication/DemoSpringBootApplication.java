package com.mohai.one.springbootapplication;

import com.mohai.one.springbootapplication.bean.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

//@SpringBootApplication
//@ComponentScan
//@SpringBootConfiguration
@EnableAutoConfiguration
//@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
//@SpringBootApplication(excludeName = {"org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration"})
public class DemoSpringBootApplication {

    @Bean
    public Runnable createRunnable(){
        return () -> System.out.println("spring boot is running!");
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(DemoSpringBootApplication.class, args);
        context.getBean(Runnable.class).run();
//        TestConfiguration testConfiguration = context.getBean(TestConfiguration.class);
//        System.out.println(testConfiguration.getUser());
//        System.out.println("是否单例："+context.isSingleton("testConfiguration"));
//        User user = (User) context.getBean("getUser");
//        System.out.println(user);
//        System.out.println(testConfiguration.getUser() == user);
//        User user1 = (User) context.getBean("getUser");
//        System.out.println(user1);
//        System.out.println(user1 == user);
//        User user2 = (User) context.getBean("getUser2");
//        System.out.println(user2);

        String[] beanDefinitionNames = context.getBeanDefinitionNames();
        for (String name : beanDefinitionNames){
            System.out.println(name);
        }

    }

}
