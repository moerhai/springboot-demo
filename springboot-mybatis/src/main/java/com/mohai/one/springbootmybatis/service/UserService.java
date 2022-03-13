package com.mohai.one.springbootmybatis.service;

import com.mohai.one.springbootmybatis.domain.User;
import com.mohai.one.springbootmybatis.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public List<User> getAllList(){
        return userMapper.getAllList();
    }

    public User getUserById(String id){
        return userMapper.getUserById(Integer.valueOf(id));
    }

    @Transactional
    public void saveUser(User user){
        userMapper.insert(user);
    }

    @Transactional
    public void editUser(User user){
        userMapper.update(user);
    }

    @Transactional
    public void deleteUser(String id){
        userMapper.delete(Integer.valueOf(id));
    }

}
