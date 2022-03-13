package com.mohai.one.springbootmvc.web;


import com.mohai.one.springbootmvc.vo.RoleVo;
import com.mohai.one.springbootmvc.vo.UserVo;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/user")
public class UserController {

    /**
     * 不定义返回类型，使用默认
     * @return
     */
    @GetMapping(value = "/get")
    public UserVo getUserVo(){
        UserVo userVo = new UserVo();
        userVo.setAge(24);
        userVo.setBirthday(new Date());
        userVo.setRealName("风清扬");
        userVo.setSex("男");
        userVo.setId(1);
        return userVo;
    }


    /**
     * 定义映射处理JSON媒体类型，返回类型为JSON
     * @return
     */
    @GetMapping(value = "/getJson",produces = MediaType.APPLICATION_JSON_VALUE)
    public UserVo getUserVoJson(){
        UserVo userVo = new UserVo();
        userVo.setAge(24);
        userVo.setBirthday(new Date());
        userVo.setRealName("风清扬");
        userVo.setSex("男");
        userVo.setId(1);
        return userVo;
    }

    /**
     * 定义映射处理XML媒体类型，返回类型为XML
     * @return
     */
    @GetMapping(value = "/getXml", produces = MediaType.APPLICATION_XML_VALUE)
    public UserVo getUserVoXml(){
        UserVo userVo = new UserVo();
        userVo.setAge(24);
        userVo.setBirthday(new Date());
        userVo.setRealName("风清扬");
        userVo.setSex("男");
        userVo.setId(1);
        return userVo;
    }

    @GetMapping(value = "/getRoleVoXml", produces = MediaType.APPLICATION_XML_VALUE)
    public RoleVo getRoleVoXml(){
        return new RoleVo("管理员","权限");
    }

}
