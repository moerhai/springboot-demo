package com.mohai.one.springbootcacheehcache.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/8/1 22:06
 */
@Entity
@Data
public class UserDTO implements Serializable {

    @Id
    private long id;
    private String name;
    private int age;
    private char sex;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date birthday;

}
