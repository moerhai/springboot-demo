package com.mohai.one.clientcredentialsgrant.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

/**
 *
 * @Auther: moerhai@qq.com
 * @Date: 2020/10/9 11:59
 */
@Configuration
@EnableAuthorizationServer
public class ClientCredentialsGrantConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    public PasswordEncoder passwordEncoder;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("client-all")
                .secret(passwordEncoder.encode("client-all-secret"))
                .authorizedGrantTypes("client_credentials") //开启客户端模式
                .accessTokenValiditySeconds(120) //访问令牌的有效期，这里设置120s
                .scopes("client_info");
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients()
                .checkTokenAccess("isAuthenticated()");
    }

}