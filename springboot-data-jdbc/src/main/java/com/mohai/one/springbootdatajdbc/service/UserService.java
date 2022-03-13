package com.mohai.one.springbootdatajdbc.service;

import com.mohai.one.springbootdatajdbc.domain.UserEntity;
import com.mohai.one.springbootdatajdbc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    //查
    public List<UserEntity> getAll(){
        List<UserEntity> userEntities = userRepository.findAll();
       return userEntities;
    }

    //查
    public List<UserEntity> getAllByName(String name){
        return userRepository.findByName(name);
    }

    //增
    @Transactional
    public int insertUser(UserEntity userEntity){
        return userRepository.insertNameAndAge(userEntity.getId(),userEntity.getName(),userEntity.getAge());
    }

    //改
    @Transactional
    public int updateUser(UserEntity userEntity){
        return userRepository.updateNameAndAge(userEntity.getId(),userEntity.getName(),userEntity.getAge());
    }

    //删
    @Transactional
    public void deleteUserById(Integer id){
        userRepository.deleteById(id);
    }

}