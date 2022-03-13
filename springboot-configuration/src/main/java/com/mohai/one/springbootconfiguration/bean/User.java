package com.mohai.one.springbootconfiguration.bean;

import java.util.Date;

public class User {

    private Integer id;
    private String realName;
    private Integer age;
    private Date birthday;

    private House house;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "id=" + id +
                ", realName='" + realName + '\'' +
                ", age=" + age +
                ", birthday=" + birthday +
                '}';
    }
}
