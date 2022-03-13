package com.mohai.one.springbootjpa.service;

import com.mohai.one.springbootjpa.domain.UserEntity;
import com.mohai.one.springbootjpa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    //查
    public List<UserEntity> getAll(){
        return userRepository.findAll();
    }

    //查
    public List<UserEntity> findAllByName(String name){
        return userRepository.findAllByName(name);
    }

    //通过id查询
    public UserEntity getOne(Integer id){
        return userRepository.findById(id).get();
    }

    //改
    @Transactional
    public UserEntity updateUser(UserEntity userEntity){
        return userRepository.saveAndFlush(userEntity);
    }

    //增
    @Transactional
    public int insertUser(UserEntity userEntity){
        return userRepository.insertNameAndAge(userEntity.getId(),userEntity.getName(),userEntity.getAge());
    }

    //删
    @Transactional
    public void deleteUserById(Integer id){
        userRepository.deleteById(id);
    }

}