package com.mohai.one.springbootrestdocs;

import com.mohai.one.springbootrestdocs.controller.UserController;
import com.mohai.one.springbootrestdocs.domain.User;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.halLinks;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/7/24 23:21
 */
@WebMvcTest(UserController.class)
//开启生成snippets文件，可通过outputDir指定路径，默认路径为target/generated-snippets
@AutoConfigureRestDocs
public class UserDocumentationTests {

    @Autowired
    private MockMvc mvc;

    /*
     *对应接口http://localhost:8080/ GET
     */
    @Test
    void testIndex() throws Exception {
        //调用服务的根"/",并指示以text/plain类型响应
        this.mvc.perform(get("/").accept(MediaType.APPLICATION_JSON))
                //断言该服务产生了预期的响应
                .andExpect(status().isOk())
                //记录对服务的调用，将代码片段写入名为index的目录中，输出到配置的目录下
                .andDo(document("index",
                        // preprocessRequest方法添加了一个删除HTTP头X-Sample的请求预处理器
                        preprocessRequest(
                                removeHeaders("X-Sample")
                        ),
                        // preprocessResponse方法添加了对内容进行重新格式化的响应预处理器
                        preprocessResponse(
                                prettyPrint()
                        ),
                        // links 为文档添加超媒体链接，默认支持 Atom 和 HAL 两种格式
                        // 会增加一个新的links.adoc文件，里面包含了链接的信息。这个文件可以在API文档中引用
                        links(
                                halLinks(),
                                //linkWithRel 描述了每个链接的关系及其说明
                                linkWithRel("self").description("当前资源的链接"),
                                linkWithRel("users").description("指向所有用户资源的链接")
                        ),
                        responseFields(
                                //HAL格式，使用_links 来表示链接
                                subsectionWithPath("_links").description("到其他资源的链接")
                        )
                ));
    }

    /**
     * 对应接口http://localhost:8080/users GET
     *
     * @throws Exception
     */
    @Test
    void userList() throws Exception {
        this.mvc.perform(get("/users").accept(MediaType.APPLICATION_JSON)
                //添加HTTP头文档
                .header("X-Sample", "Test HTTP Header"))
                .andExpect(status().isOk())
                .andDo(document("user-list",
                        //requestHeaders方法用来添加 HTTP 请求中头的文档
                        requestHeaders(
                                // headerWithName方法描述一个 HTTP 头的名称和含义
                                // 生成代码片段的文件名是 request-headers.adoc
                                headerWithName("X-Sample").description("测试请求 HTTP 头")
                        )
                ));
    }

    /**
     * 对应接口http://localhost:8080/user?id=1 GET
     *
     * @throws Exception
     */
    @Test
    void userInfo() throws Exception {
        mvc.perform(get("/user?id=1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("user-info",
                        //requestParameters方法用来添加请求参数的文档
                        requestParameters(
                                //parameterWithName方法表示单个参数的名称和含义
                                //生成代码片段的文件名是 request-parameters.adoc
                                parameterWithName("id").description("用户ID")
                        )
                ));
    }

    /**
     * 对应接口http://localhost:8080/user POST
     *
     * @throws Exception
     */
    @Test
    void saveUser() throws Exception {
        //注意属性对应关系，描述信息与属性个数要一致
        Map<String, Object> user = new HashMap<>();
        user.put("id", 2);
        user.put("username", "test");
        user.put("email", "test@example.com");
        user.put("phone", "655888");
        //约束对象
        ConstraintDescriptions userConstraints = new ConstraintDescriptions(User.class);
        mvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON).content(JSONObject.toJSONString(user)))
                .andExpect(status().isOk())
                .andDo(document("save-user",
                        //通过descriptionsForProperty方法来得到每个字段的约束的描述信息
                        requestFields(
                                fieldWithPath("id")
                                        .description(String.format("用户ID（%s）",
                                                userConstraints.descriptionsForProperty("id"))),
                                fieldWithPath("username").type(JsonFieldType.STRING)
                                        .description(String.format("用户名（%s）",
                                                userConstraints.descriptionsForProperty("username"))),
                                fieldWithPath("phone").type(JsonFieldType.STRING)
                                        .description(String.format("电话（%s）",
                                                userConstraints.descriptionsForProperty("phone"))),
                                fieldWithPath("email").type(JsonFieldType.STRING)
                                        .description(String.format("Email 地址（%s）",
                                                userConstraints.descriptionsForProperty("email")))
                        )
                ));
    }


    /**
     * 对应接口http://localhost:8080/user PUT
     *
     * @throws Exception
     */
    @Test
    void updateUser() throws Exception {
        Map<String, Object> user = new HashMap<>();
        user.put("id", 2);
        user.put("username", "jack");
        user.put("email", "jack@example.com");
        //约束对象
        ConstraintDescriptions userConstraints = new ConstraintDescriptions(User.class);
        mvc.perform(put("/user").contentType(MediaType.APPLICATION_JSON).content(JSONObject.toJSONString(user)))
                .andExpect(status().isOk())
                .andDo(document("update-user",
                        requestFields(
                                fieldWithPath("id")
                                        .description("用户ID").attributes(
                                        key("constraints").value(
                                                userConstraints.descriptionsForProperty("id"))),
                                fieldWithPath("username")
                                        // 使用 attributes 方法来添加额外的属性 constraints
                                        // 属性值是字段约束的描述
                                        .description("用户名").attributes(
                                        key("constraints").value(
                                                userConstraints.descriptionsForProperty("username"))
                                ),
                                fieldWithPath("email")
                                        .description("Email 地址")
                                        .attributes(key("constraints").value(
                                                userConstraints.descriptionsForProperty("email")))

                        )
                ));
    }


    /**
     * 对应接口http://localhost:8080/user/1 DELETE
     *
     * @throws Exception
     */
    @Test
    void deleteUser() throws Exception {
        mvc.perform(delete("/user/{id}", 1))
                .andExpect(status().isOk())
                .andDo(document("delete-user",
                        //pathParameters 方法用来添加路径参数的文档
                        pathParameters(
                                //parameterWithName方法表示单个参数的名称和含义
                                //生成代码片段的文件名是 path-parameters.adoc
                                parameterWithName("id").description("用户ID")
                        )
                ));
    }
}
