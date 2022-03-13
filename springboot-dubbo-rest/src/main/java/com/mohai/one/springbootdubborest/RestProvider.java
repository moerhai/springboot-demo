package com.mohai.one.springbootdubborest;

import com.mohai.one.springbootdubborest.config.ProviderServerConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/7/24 07:42
 */
public class RestProvider {

    public static void main(String[] args) throws IOException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ProviderServerConfig.class);
        context.start();
        System.in.read();
    }

}
