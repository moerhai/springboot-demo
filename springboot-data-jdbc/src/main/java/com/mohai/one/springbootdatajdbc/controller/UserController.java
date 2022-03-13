package com.mohai.one.springbootdatajdbc.controller;

import com.mohai.one.springbootdatajdbc.domain.UserEntity;
import com.mohai.one.springbootdatajdbc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/findAll")
    public List<UserEntity> findAll(){
        return userService.getAll();
    }

    @RequestMapping("/findAllByName")
    public List<UserEntity> findAllByName(String name){
        return userService.getAllByName(name);
    }

    @RequestMapping("/save")
    public int save(@RequestBody UserEntity userEntity){
        return userService.insertUser(userEntity);
    }

    @RequestMapping("/edit")
    public int edit(@RequestBody UserEntity userEntity){
        return userService.updateUser(userEntity);
    }

    @RequestMapping("/delete")
    public int delete(@RequestParam int id){
         userService.deleteUserById(id);
         return 1;
    }
}
