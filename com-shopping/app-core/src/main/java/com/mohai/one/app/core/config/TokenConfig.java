package com.mohai.one.app.core.config;

import com.mohai.one.app.core.filter.JwtTokenAuthenticationFilter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置登录授权过滤器和注入TokenProperties
 * @Auther: moerhai@qq.com
 * @Date: 2020/12/14 00:39
 */
@Configuration
@EnableConfigurationProperties(TokenProperties.class)
public class TokenConfig {

    private final TokenProperties tokenProperties;

    public TokenConfig(TokenProperties tokenProperties) {
        this.tokenProperties = tokenProperties;
    }

    @Bean
    public JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter() {
        return new JwtTokenAuthenticationFilter();
    }

//    @Bean
//    public TokenStore tokenStore() {
//        RedisTokenStore tokenStore = new RedisTokenStore(redisConnectionFactory);
//        tokenStore.setPrefix(tokenProperties.getOauthPrefix());
//        tokenStore.setSerializationStrategy(new FastJsonRedisTokenStoreSerializationStrategy());
//        return tokenStore;

//        return new JwtTokenStore(accessTokenConverter()) {
//            @Override
//            public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
//                //添加Jwt Token白名单,将Jwt以jti为Key存入redis中，并保持与原Jwt有一致的时效性
//                if (token.getAdditionalInformation().containsKey("jti")) {
//                    String jti = token.getAdditionalInformation().get("jti").toString();
//                    redisTemplate.opsForValue().set(jti, token.getValue(), token.getExpiresIn(), TimeUnit.SECONDS);
//                }
//                super.storeAccessToken(token, authentication);
//            }
//            @Override
//            public void removeAccessToken(OAuth2AccessToken token) {
//                if (token.getAdditionalInformation().containsKey("jti")) {
//                    String jti = token.getAdditionalInformation().get("jti").toString();
//                    redisTemplate.delete(jti);
//                }
//                super.removeAccessToken(token);
//            }
//        };
 //   }

}