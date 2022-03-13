package com.mohai.one.springbootdubborest.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.mohai.one.springbootdubborest.domain.User;
import com.mohai.one.springbootdubborest.service.UserService;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/7/23 08:00
 */
//注意这里的注解为com.alibaba.dubbo.config.annotation.Service
@Service(interfaceClass = UserService.class)
public class UserServiceImpl implements UserService{

    @Override
    public String registerUser(User user) {
        return "注册成功："+user;
    }

    @Override
    public User getUser(Long id) {
        return new User(id,"username_"+id);
    }
}