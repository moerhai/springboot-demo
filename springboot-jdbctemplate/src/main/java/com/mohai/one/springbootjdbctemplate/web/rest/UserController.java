package com.mohai.one.springbootjdbctemplate.web.rest;

import com.mohai.one.springbootjdbctemplate.domain.UserEntity;
import com.mohai.one.springbootjdbctemplate.service.UserService;
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

    @RequestMapping("/save")
    public int save(@RequestBody UserEntity userEntity){
        return userService.insertUser(userEntity);
    }

    @RequestMapping("/edit")
    public int edit(@RequestBody UserEntity userEntity){
        return userService.updateUserById(userEntity);
    }

    @RequestMapping("/delete")
    public int delete(@RequestParam int id){
        return userService.deleteUserById(id);
    }
}
