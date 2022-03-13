package com.mohai.one.springbootsecurityoauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

/**
 * 配置资源服务器
 * @Auther: moerhai@qq.com
 * @Date: 2020/9/21 00:28
 */
@Configuration
@EnableResourceServer // 标识服务是一个资源服务器
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private static final String DEFAULT_RESOURCE_ID = "rid";

    /**
     * 远程调用授权服务器帮忙校验token
     * @return
     */
    @Primary
    @Bean
    public RemoteTokenServices remoteTokenServices() {
        final RemoteTokenServices tokenServices = new RemoteTokenServices();
        // 设置授权服务器check_token端点完整地址
        tokenServices.setCheckTokenEndpointUrl("http://localhost:8080/oauth/check_token");
        // 设置客户端id与secret，注意：这里的client_secret值不能使用passwordEncoder加密！
        tokenServices.setClientId("client-all");
        tokenServices.setClientSecret("client-all-secret");
        return tokenServices;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(DEFAULT_RESOURCE_ID) // 配置资源id，这里的资源id和授权服务器中的资源id一致
                .stateless(true); // 设置这些资源仅基于令牌认证
    }

    // 配置 URL 访问权限
    @Override
    public void configure(HttpSecurity http) throws Exception {
        // 设置创建session策略
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
        // 设置资源权限
        http.authorizeRequests()
                .mvcMatchers("/admin/**").hasAuthority("ADMIN")
                .mvcMatchers("/user/**").hasAuthority("USER")
                // 必须认证过后才可以访问
                .anyRequest().authenticated(); // 所有请求必须授权
    }
}