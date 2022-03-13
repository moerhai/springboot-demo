package com.mohai.one.springbootbatch.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/8/10 23:55
 */

@Entity
@Table(name = "user")
public class UserDTO {

    @Id // 表名实体唯一标识
    @GeneratedValue(strategy = GenerationType.IDENTITY) //主键自动生成策略
    private int id;
    @Column
    @Size(max = 10)
    @NotBlank(message = "姓名不能为空")
    private String name;
    @Column
    private int age;
    @Column
    private Date createTime;
    private byte status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }
}
