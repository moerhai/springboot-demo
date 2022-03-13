package com.mohai.one.springbootrestdocs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.documentationConfiguration;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/7/25 07:58
 */
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public class JUnit5WebTestClientExampleTests {

    private WebTestClient webTestClient;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext,
                      RestDocumentationContextProvider restDocumentation) {
        //配置测试客户端，添加ExchangeFilterFunction，通过WebTestClientRestDocumentation获取WebTestClientRestDocumentationConfigurer配置
        this.webTestClient = WebTestClient.bindToApplicationContext(webApplicationContext)
                .configureClient()
                .filter(documentationConfiguration(restDocumentation))
                .build();
    }

    @Test
    void tes() throws Exception{
        //调用服务的根（/）并指示application/json需要响应
        this.webTestClient.get().uri("/").accept(MediaType.APPLICATION_JSON)
                //断言该服务产生了预期的响应
                .exchange().expectStatus().isOk()
                //记录对服务的调用，将代码片段写入名为的目录中index ，该目录将位于已配置的输出目录下
                .expectBody().consumeWith(document("index"));
    }

}
