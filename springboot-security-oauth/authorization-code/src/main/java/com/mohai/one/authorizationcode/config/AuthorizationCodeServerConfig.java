package com.mohai.one.authorizationcode.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * 配置授权服务器
 *    授权服务器和资源服务器可以是同一台服务器，也可以是不同服务器
 * @Auther: moerhai@qq.com
 * @Date: 2020/10/3 17:23
 */
// 授权服务器配置
@Configuration
@EnableAuthorizationServer // 开启授权服务
public class AuthorizationCodeServerConfig extends AuthorizationServerConfigurerAdapter {

    private static final String DEFAULT_RESOURCE_ID = "rid";

    // 用来将令牌信息存储到内存中
    @Autowired(required = false)
    TokenStore inMemoryTokenStore;

    // 将为刷新 token 提供支持
    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;

    // 将令牌信息存储到 redis 中
//    @Autowired
//    RedisConnectionFactory redisConnectionFactory;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.tokenStore(inMemoryTokenStore) //配置令牌的存储（这里存放在内存中）
                .userDetailsService(userDetailsService);

//        endpoints
//                .tokenStore(new RedisTokenStore(redisConnectionFactory)) //配置令牌存放在Redis中
//                .authenticationManager(authenticationManager)
//                .userDetailsService(userDetailsService);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        // 允许表单提交
        security.allowFormAuthenticationForClients()
                .checkTokenAccess("isAuthenticated()");
    }

    /**
     * http://localhost:8080/oauth/authorize?client_id=client-all&client_secret=client-all-secret&response_type=code
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("client-all") // 客户端唯一标识
                .secret(passwordEncoder.encode("client-all-secret")) // 客户端的密码，这里的密码应该是加密后的
                .authorizedGrantTypes("authorization_code") // 授权模式标识
                .scopes("client_info") // 作用域
                .resourceIds(DEFAULT_RESOURCE_ID) // 资源id，供资源服务器进行验证
                .redirectUris("http://api.open.com/callback"); // 回调地址
    }


//    // 配置 password 授权模式
//    @Override
//    public void configure(ClientDetailsServiceConfigurer clients)
//            throws Exception {
//        // 配置两个客户端，一个用于client认证，一个用于password认证
//        clients.inMemory()
//                .withClient("client_1")
//                .resourceIds(DEFAULT_RESOURCE_ID) //配置资源id
//                .authorizedGrantTypes("client_credentials", "refresh_token") //授权模式为client_credentials和refresh_token两种
//                .scopes("all")
//                .authorities("client")
//                .secret(passwordEncoder.encode("123456")) //密码
//                .and()
//                .withClient("client_2")
//                .resourceIds(DEFAULT_RESOURCE_ID) //配置资源id
//                .authorizedGrantTypes("password", "refresh_token") //授权模式为password和refresh_token两种
//                .scopes("select")
//                .authorities("client")
//                .secret(passwordEncoder.encode("123456")); //密码
//    }

}
