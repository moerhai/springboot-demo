package com.mohai.one.springbootmybatis.controller;

import com.mohai.one.springbootmybatis.domain.User;
import com.mohai.one.springbootmybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    //查询所有
    @RequestMapping("/getAllList")
    public List<User> getUserList(){
        return userService.getAllList();
    }

    //通过主键Id查询
    @RequestMapping("/getOne/{id}")
    public User getUserById(@PathVariable String id){
        return userService.getUserById(id);
    }

    //新增用户
    @RequestMapping("/save")
    public String saveUser(@RequestBody User user){
        userService.saveUser(user);
        return "SUCCESS";
    }

    //修改用户
    @RequestMapping("/update")
    public String editUser(@RequestBody User user){
        userService.editUser(user);
        return "SUCCESS";
    }

    //删除用户
    @RequestMapping("/deleteOne/{id}")
    public String deleteUser(@PathVariable String id){
        userService.deleteUser(id);
        return "SUCCESS";
    }

}