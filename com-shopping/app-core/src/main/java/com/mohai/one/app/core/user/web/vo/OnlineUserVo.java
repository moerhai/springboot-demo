package com.mohai.one.app.core.user.web.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Date;

/**
 * 登录后的用户信息
 * @Auther: moerhai@qq.com
 * @Date: 2020/12/27 00:28
 */
public class OnlineUserVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonIgnore
    private String token;

    private String userName;

    private String nickName;

    private String browser;

    private String ip;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date loginTime;

    private String userType;

    public OnlineUserVo(String token, String userName, String nickName, String browser, String ip, Date loginTime) {
        this.token = token;
        this.userName = userName;
        this.nickName = nickName;
        this.browser = browser;
        this.ip = ip;
        this.loginTime = loginTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}