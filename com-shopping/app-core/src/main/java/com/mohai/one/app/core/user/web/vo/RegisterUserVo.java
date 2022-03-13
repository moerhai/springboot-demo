package com.mohai.one.app.core.user.web.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;


/**
 * 注册用户信息表单
 *
 * @Auther: moerhai@qq.com
 * @Date: 2020/11/26 00:05
 */
@ApiModel(value = "注册表单")
public class RegisterUserVo {

    @ApiModelProperty(value = "手机号")
    @NotBlank(message="手机号不能为空")
    private String mobile;

    @ApiModelProperty(value = "密码")
    @NotBlank(message="密码不能为空")
    private String password;

}
