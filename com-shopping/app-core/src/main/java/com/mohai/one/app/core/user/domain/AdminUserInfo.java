package com.mohai.one.app.core.user.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mohai.one.app.core.constant.ComConstants;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户信息
 * @Auther: moerhai@qq.com
 * @Date: 2020/11/29 15:27
 */
@Table(name = "admin_user")
public class AdminUserInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name= "user_id")
    private long userId;
    @Column(name= "username")
    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name= "password")
    private String password;
    @JsonIgnore
    @Column(name= "salt")
    private String salt;
    @Column(name= "mobile")
    private String mobile;
    @Column(name= "email")
    private String email;
    // 用户类型：0-普通用户、1-管理员
    @Column(name= "type")
    private String type;
    // 用户状态 0-正常 1-冻结
    @Column(name= "status")
    private String status;
    @Column(name= "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    @Column(name= "update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public boolean isEnable(){
        return ComConstants.STATUS_0.equals(status);
    }
}