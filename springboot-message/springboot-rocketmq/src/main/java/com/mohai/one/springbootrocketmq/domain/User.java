package com.mohai.one.springbootrocketmq.domain;

import java.io.Serializable;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/10/6 01:52
 */
public class User implements Serializable {
    private String loginName;
    private String pwd;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
