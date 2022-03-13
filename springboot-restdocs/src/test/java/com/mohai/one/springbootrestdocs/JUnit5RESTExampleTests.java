package com.mohai.one.springbootrestdocs;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.core.Is.is;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/7/25 07:58
 */
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public class JUnit5RESTExampleTests {

    private RequestSpecification spec;

    @BeforeEach
    public void setUp(RestDocumentationContextProvider restDocumentation) {
        //为filter添加RestAssuredRestDocumentationConfigurer配置，通过RestAssuredRestDocumentation获取该配置
        this.spec = new RequestSpecBuilder()
                .addFilter(documentationConfiguration(restDocumentation))
                .build();
    }

    @Test
    void test() throws Exception{
        //应用@BeforeEach方法中初始化的规范
        RestAssured.given(this.spec)
                //表示application/json需要答复
                .accept("application/json")
                //记录对服务的调用，将代码片段写入名为的目录中index ，该目录将位于已配置的输出目录下
                .filter(document("index"))
                //调用服务的根目录（/）
                .when().get("/")
                //断言服务产生了预期的响应
                .then().assertThat().statusCode(is(200));
    }

}
