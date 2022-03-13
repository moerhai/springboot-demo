package com.mohai.one.app.core.user.domain;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 权限信息
 * @Auther: moerhai@qq.com
 * @Date: 2020/12/18 21:49
 */
@Table(name = "admin_permission")
public class AdminPermission implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name= "perm_id")
    private long permId;
    @Column(name= "perm_code")
    private String permCode;
    @Column(name= "perm_name")
    private String permName;

    public long getPermId() {
        return permId;
    }

    public void setPermId(long permId) {
        this.permId = permId;
    }

    public String getPermCode() {
        return permCode;
    }

    public void setPermCode(String permCode) {
        this.permCode = permCode;
    }

    public String getPermName() {
        return permName;
    }

    public void setPermName(String permName) {
        this.permName = permName;
    }
}