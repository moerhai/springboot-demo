package com.mohai.one.springbootjpa.domain;

import javax.persistence.*;

@Entity //必选注解，声明和数据库中user表关联
@Table(name = "user") //可选注解，声明实体对应的表信息
public class UserEntity {

    @Id // 表名实体唯一标识
    @GeneratedValue(strategy = GenerationType.IDENTITY) //主键自动生成策略
    private Integer id;
    //@Column定义列名和属性，默认为字段名
    @Column
    private String name;
    @Column
    private int age;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
}
