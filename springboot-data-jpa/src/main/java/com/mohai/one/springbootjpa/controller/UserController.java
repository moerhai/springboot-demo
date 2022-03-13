package com.mohai.one.springbootjpa.controller;

import com.mohai.one.springbootjpa.domain.UserEntity;
import com.mohai.one.springbootjpa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Created by moerhai@qq.com
 */
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
        return userService.findAllByName(name);
    }

    //通过主键Id查询
    @RequestMapping("/getOne/{id}")
    public UserEntity getUserById(@PathVariable Integer id){
        return userService.getOne(id);
    }

    @RequestMapping("/save")
    public int save(@RequestBody UserEntity userEntity){
        return userService.insertUser(userEntity);
    }

    @RequestMapping("/edit")
    public UserEntity edit(@RequestBody UserEntity userEntity){
        return userService.updateUser(userEntity);
    }

    @RequestMapping("/delete")
    public int delete(@RequestParam("id") Integer id){
         userService.deleteUserById(id);
         return 1;
    }
}
