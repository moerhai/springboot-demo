package com.mohai.one.springbootdubborest.config;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/7/23 23:55
 */
@Configuration
//指定需要扫描Dubbo服务的包名
@EnableDubbo(scanBasePackages = "com.mohai.one.springbootdubborest.service")
public class ProviderServerConfig {

    /**
     * 定义协议配置，设置rest协议
     * @return
     */
    @Bean
    public ProtocolConfig protocolConfig() {
        //指定服务提供方按照REST来暴露服务
        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setName("rest");
        protocolConfig.setPort(8080);
        protocolConfig.setServer("netty");
        return protocolConfig;
    }

    /**
     *服务注册配置
     * @return
     */
    @Bean
    public RegistryConfig registryConfig() {
        //指定服务提供方所使用的服务注册机制，这里不进行注册
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setRegister(false);
        return registryConfig;
    }

    /**
     * 应用配置，设置应用名称
     * @return
     */
    @Bean
    public ApplicationConfig applicationConfig() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("rest-provider");
        return applicationConfig;
    }

}
