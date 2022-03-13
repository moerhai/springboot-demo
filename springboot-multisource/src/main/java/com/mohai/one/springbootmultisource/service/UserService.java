package com.mohai.one.springbootmultisource.service;

import com.mohai.one.springbootmultisource.dao.UserDao;
import com.mohai.one.springbootmultisource.domain.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Transactional
    public void save(UserEntity userEntity){
        userDao.save(userEntity);
    }

    @Transactional
    public void edit(UserEntity userEntity){

    }

    public List<UserEntity> getAll(UserEntity userEntity){
        return userDao.findAll();
    }

}