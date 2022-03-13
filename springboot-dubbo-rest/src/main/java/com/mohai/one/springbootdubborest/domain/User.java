package com.mohai.one.springbootdubborest.domain;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/7/23 07:59
 */
@XmlRootElement
public class User implements Serializable {

    private Long id;
    private String name;

    public User(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

}
