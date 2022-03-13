package com.mohai.one.springbootswagger.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/7/18 00:18
 */
@ApiModel(description="用户对象user")
public class UserDTO {

    @ApiModelProperty(value="用户id")
    private int id;
    @ApiModelProperty(value="用户名",name="username")
    private String username;
    @ApiModelProperty(value="登录时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date loginTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }
}
