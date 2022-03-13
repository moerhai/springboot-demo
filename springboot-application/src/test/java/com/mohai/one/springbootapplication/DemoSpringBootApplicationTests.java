package com.mohai.one.springbootapplication;

import com.mohai.one.springbootapplication.config.MyTestConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.util.ClassUtils;

@SpringBootTest
@ContextConfiguration
class DemoSpringBootApplicationTests {

    @Test
    void contextLoads() {
    }

    public static void main(String[] args) {
        System.out.println(ClassUtils.getPackageName(MyTestConfiguration.class));
    }

}
