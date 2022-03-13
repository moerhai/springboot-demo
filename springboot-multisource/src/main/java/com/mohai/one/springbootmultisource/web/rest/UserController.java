package com.mohai.one.springbootmultisource.web.rest;

import com.mohai.one.springbootmultisource.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/edit")
    public String editUser(@RequestParam String id){


        return "SUCCESS";
    }

}
