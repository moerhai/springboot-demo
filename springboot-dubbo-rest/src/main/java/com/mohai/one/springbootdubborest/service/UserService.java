package com.mohai.one.springbootdubborest.service;

import com.mohai.one.springbootdubborest.domain.User;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/7/23 07:58
 */
@Path("users") //指定访问UserService的URL相对路径是/users
@Consumes({MediaType.APPLICATION_JSON})//指定参数为JSON格式的数据
@Produces({MediaType.APPLICATION_JSON})//指定返回值为JSON格式的数据
public interface UserService {

    @POST //指定访问registerUser()用HTTP POST方法
    @Path("register") //指定访问registerUser()方法的URL相对路径是/register
    @Produces({MediaType.TEXT_HTML})
    String registerUser(User user);

    @GET //指定访问getUser()用HTTP GET方法
    @Path("{id: \\d+}") //指定访问getUser()方法的URL,/users/1
    User getUser(@PathParam("id") Long id);
}
