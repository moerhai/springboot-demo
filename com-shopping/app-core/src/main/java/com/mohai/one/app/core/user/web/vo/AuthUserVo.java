package com.mohai.one.app.core.user.web.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 登录用户所需信息
 * @Auther: moerhai@qq.com
 * @Date: 2020/11/26 00:05
 */
@ApiModel(value= "用户信息")
public class AuthUserVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户账号")
    private String username;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "用户密码",required = true)
    @NotBlank(message="密码不能为空")
    private String password;

    @ApiModelProperty(value = "uuid",notes = "取后端返回值上送")
    private String uuId;
    @ApiModelProperty(value = "验证码")
    private String code;

    public String getUuId() {
        return uuId;
    }

    public void setUuId(String uuId) {
        this.uuId = uuId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "AuthUserVo{" +
                "username='" + username + '\'' +
                ", mobile='" + mobile + '\'' +
                '}';
    }
}