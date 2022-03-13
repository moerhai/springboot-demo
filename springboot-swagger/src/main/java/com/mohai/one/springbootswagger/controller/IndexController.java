package com.mohai.one.springbootswagger.controller;

import com.mohai.one.springbootswagger.domain.UserDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/7/17 23:20
 */
@RestController
@Api("页面首页")
public class IndexController {

    @ApiOperation("欢迎页面")
    @GetMapping("/index")
    @ApiImplicitParam(name = "name",value = "姓名",dataType = "String")
    public String index(String name){
        return "Welcome " + name + " to my site";
    }

    @ApiOperation("更新用户信息")
    @PostMapping("/updateUserInfo")
    public UserDTO updateUserInfo(@RequestBody  @ApiParam(name="用户对象",value="传入json格式",required=true) UserDTO user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername("test");
        userDTO.setLoginTime(new Date());
        return userDTO;
    }

}