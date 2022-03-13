package com.mohai.one.springbootswagger.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/7/17 23:20
 */
@Configuration
@EnableSwagger2 //声明启动Swagger2
//声明属性是否可用
@ConditionalOnProperty(name = "spring.swagger.enable", havingValue = "true", matchIfMissing=true)
public class SwaggerConfig {

    /**
     * 注入 Docket，添加扫描的包路径
     *
     * @return
     */
    @Bean
    public Docket customDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                //ApiInfo用于描述Api文件的基础信息
                .apiInfo(new ApiInfoBuilder()
                        //标题
                        .title("Swagger2文档")
                        //描述
                        .description("Rest风格接口")
                        //版本号
                        .version("1.0.0")
                        .build())
                .select()
                //定义扫描的swagger接口包路径
                .apis(RequestHandlerSelectors.basePackage("com.mohai.one.springbootswagger.controller"))
                //所有路径都满足这个条件
                .paths(PathSelectors.any())
                .build();
    }

}
