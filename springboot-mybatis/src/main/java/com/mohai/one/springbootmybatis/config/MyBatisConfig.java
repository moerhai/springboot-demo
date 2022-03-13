package com.mohai.one.springbootmybatis.config;

import com.mohai.one.springbootmybatis.interceptor.CommonInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//提供可配置化是否启用
@ConditionalOnProperty(prefix = "common.interceptor", name = "enabled", matchIfMissing = true)
public class MyBatisConfig {

    /**
     * 第一种，自定义配置
     */
    @Bean
    ConfigurationCustomizer mybatisConfigurationCustomizer() {
        return new ConfigurationCustomizer() {
            @Override
            public void customize(org.apache.ibatis.session.Configuration configuration) {
                // do something
               // configuration.addInterceptor(new CommonInterceptor());

            }
        };
    }

    /**
     * 第二种直接注入，切勿重复注入
     * @return
     */
    @Bean
    public Interceptor getCommonInterceptor(){
        CommonInterceptor commonInterceptor = new CommonInterceptor();
        return commonInterceptor;
    }

}