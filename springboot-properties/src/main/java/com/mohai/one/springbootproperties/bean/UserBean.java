package com.mohai.one.springbootproperties.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Component 将本来标识为一个Spring 组件;
 * 因为只有是容器中的组件，容器才会为@ConfigurationProperties提供此注入功能
 * @ConfigurationProperties(prefix="user") 表示将本类中的所有属性和配置文件中相关的配置进行绑定;
 * prefix = "user" 表示将配置文件中key为user的下面所有的属性与本类属性进行一一映射注入值，如果配置文件中
 * 不存在"user"的key，则不会为POJO注入值，属性值仍然为默认值
 * @PropertySource(value={"classpath:user.properties"}) 指明加载类路径下的哪个配置文件来注入值
 */
@Component
@PropertySource(value = {"classpath:user.properties"},encoding = "utf-8")
@ConfigurationProperties(prefix = "user")
public class UserBean {

    private Integer id;
    private String realName;
    private Integer age;
    private Date birthday;

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
