package com.mohai.one.springbootrestdocs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.templates.TemplateFormats;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.cli.CliDocumentation.curlRequest;
import static org.springframework.restdocs.http.HttpDocumentation.httpResponse;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestBody;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/7/25 07:58
 */
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public class JUnit5MockMvcExampleTests {

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext,
                      RestDocumentationContextProvider restDocumentation) {
        //创建实添加MockMvcRestDocumentationConfigurer配置，通过MockMvcRestDocumentation获取
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .build();

        //配置 Spring REST Docs
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation)
                        .uris().withScheme("https").withHost("api.mohai.com").withPort(443)
                        .and()
                        .snippets().withDefaults(curlRequest(), requestBody(), httpResponse()))
                .build();

        //使用 Markdown 作为文档格式
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
//                .apply(documentationConfiguration(restDocumentation)
//                        .snippets().withTemplateFormat(TemplateFormats.markdown()))
//                .build();
    }


    @Test
    void test() throws Exception{
        //调用服务的根（/）并指示application/json需要响应
        this.mockMvc.perform(get("/").accept(MediaType.APPLICATION_JSON))
                //断言该服务产生了预期的响应
                .andExpect(status().isOk())
                //记录对服务的调用，将代码片段写入名为的目录中index ，该目录将位于已配置的输出目录下
                .andDo(document("index"));
    }

}
