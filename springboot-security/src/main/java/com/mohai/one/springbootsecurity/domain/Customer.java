package com.mohai.one.springbootsecurity.domain;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/9/13 19:18
 */
public class Customer {

    private String userName;
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
