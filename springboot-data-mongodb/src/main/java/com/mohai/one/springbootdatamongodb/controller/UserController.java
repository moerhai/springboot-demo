package com.mohai.one.springbootdatamongodb.controller;

import com.mohai.one.springbootdatamongodb.domain.UserDTO;
import com.mohai.one.springbootdatamongodb.service.UserService;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/7/12 00:49
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/findAll")
    public List<UserDTO> findAll(){
        return userService.findAll();
    }

    @GetMapping("/get/{id}")
    public UserDTO getOne(@PathVariable Integer id){
        return userService.getOneById(id);
    }

    @PostMapping("/save")
    public UserDTO save(@RequestBody UserDTO userDTO){
        return userService.insert(userDTO);
    }

    @PostMapping("/update")
    public UpdateResult update(@RequestBody UserDTO userDTO){
        return userService.updateResult(userDTO);
    }

    @DeleteMapping("/delete/{id}")
    public DeleteResult update(@PathVariable Integer id){
        return userService.deleteResult(id);
    }

}