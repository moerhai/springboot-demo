package com.mohai.one.implicitgrant.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

/**
 * 授权服务器配置
 * @Auther: moerhai@qq.com
 * @Date: 2020/10/9 09:10
 */
@Configuration
@EnableAuthorizationServer //开启授权服务
public class ImplicitGrantConfig extends AuthorizationServerConfigurerAdapter {

    private static final String DEFAULT_RESOURCE_ID = "rid";

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        //允许表单提交
        security.allowFormAuthenticationForClients()
                .checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("client-all") //客户端唯一标识
                .secret(passwordEncoder.encode("client-all-secret")) //资源服务器校验token时用的客户端信息，仅需要client_id与密码
                .authorizedGrantTypes("implicit") //隐式授权模式标识
                .accessTokenValiditySeconds(120) //访问令牌的有效期，这里设置120s
                .scopes("client_info") //作用域
                .resourceIds(DEFAULT_RESOURCE_ID) //资源id
                .redirectUris("http://api.open.com/callback"); //回调地址
    }
}