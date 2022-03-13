package com.mohai.one.app.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * swagger文档配置
 * @Auther: moerhai@qq.com
 * @Date: 2020/11/22 15:21
 */
@Configuration
@EnableSwagger2 //声明启动Swagger2
//声明属性是否可用
@ConditionalOnProperty(name = "swagger.enable", havingValue = "true", matchIfMissing=true)
public class SwaggerConfig {

    @Value("${swagger.base-package}")
    private String basePackage;

    @Value("${swagger.title}")
    private String title;

    @Value("${swagger.description}")
    private String description;

    @Value("${swagger.version}")
    private String version;

//    @Value("${swagger.auth-server}")
//    private String authServer;

    /**
     * 注入 Docket，添加扫描的包路径
     *
     * @return
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                //ApiInfo用于描述Api文件的基础信息
                .apiInfo(new ApiInfoBuilder()
                        //标题
                        .title(title)
                        //描述
                        .description(description)
                        //版本号
                        .version(version)
                        .build())
                .select()
                //定义扫描的swagger接口包路径
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                //所有路径都满足这个条件
                .paths(PathSelectors.any())
                .build()
                //添加登录认证
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
//                .securitySchemes(Collections.singletonList(securityScheme()))
//                .securityContexts(Collections.singletonList(securityContext()));
    }

    /**
     * Swagger三种认证方式，即SecurityScheme的三种实现：
     * ApiKey：支持header和query两种认证方式；
     * BasicAuth：简单认证；
     * OAuth：基于OAuth2的认证方式。
     */

    /**
     * ApiKey：支持header和query两种认证方式；
     * @return
     */
    private List<ApiKey> securitySchemes() {
        //设置请求头信息
        List<ApiKey> list = new ArrayList<>();
        list.add(new ApiKey("Authorization", "Authorization", "header"));
        //list.add(new ApiKey("access_token", "access_token", "header"));
        return list;
    }

    private List<SecurityContext> securityContexts() {
        //设置需要登录认证的路径
        List<SecurityContext> result = new ArrayList<>();
        result.add(securityContext());
        return result;
    }

//    /**
//     * OAuth：基于OAuth2的认证方式。
//     * @return
//     */
//    private SecurityScheme securityScheme() {
//        GrantType grantType = new ResourceOwnerPasswordCredentialsGrant(authServer + "/oauth/token");
//
//        return new OAuthBuilder()
//                .name("Authorization")
//                .grantTypes(Collections.singletonList(grantType))
//                .scopes(Arrays.asList(scopes()))
//                .build();
//    }
//
    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(Collections.singletonList(new SecurityReference("Authorization", scopes())))
                .forPaths(PathSelectors.any())
                .build();
    }

    private AuthorizationScope[] scopes() {
        return new AuthorizationScope[]{
                new AuthorizationScope("all", "All scope is trusted!")
        };
    }
}