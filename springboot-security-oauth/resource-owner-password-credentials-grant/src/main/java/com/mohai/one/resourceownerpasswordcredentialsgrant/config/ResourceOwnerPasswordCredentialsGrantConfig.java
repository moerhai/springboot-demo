package com.mohai.one.resourceownerpasswordcredentialsgrant.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

/**
 *
 * @Auther: moerhai@qq.com
 * @Date: 2020/10/9 10:39
 */
@Configuration
@EnableAuthorizationServer
public class ResourceOwnerPasswordCredentialsGrantConfig extends AuthorizationServerConfigurerAdapter {

    /**
     * 密码模式需要注入认证管理器
     */
    @Autowired
    public AuthenticationManager authenticationManager;

    @Autowired
    public PasswordEncoder passwordEncoder;

    //配置客户端
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("client-all")
                .secret(passwordEncoder.encode("client-all-secret"))
                .authorizedGrantTypes("password") //开启密码模式
                .accessTokenValiditySeconds(120) //访问令牌的有效期，这里设置120s
                .scopes("client_info");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        // 密码模式必须添加authenticationManager
        endpoints.authenticationManager(authenticationManager);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients()
                .checkTokenAccess("isAuthenticated()");
    }

}