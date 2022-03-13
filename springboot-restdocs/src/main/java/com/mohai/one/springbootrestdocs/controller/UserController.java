package com.mohai.one.springbootrestdocs.controller;

import com.mohai.one.springbootrestdocs.domain.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/7/24 23:46
 */
@RestController
public class UserController {

    @RequestMapping("/")
    public Map index(){
        Map map = new HashMap();
        Map link = new HashMap();
        link.put("self","http://localhost:8080/");
        link.put("users","http://localhost:8080/users");
        map.put("_links",link);
        return map;
    }

    @GetMapping("/users")
    public List<User> userList(){
        List<User> users = new ArrayList<>();
        User user=new User();
        user.setId(1l);
        user.setUsername("admin");
        user.setEmail("admin@example.com");
        user.setPhone("666666");
        users.add(user);
        return users;
    }

    @GetMapping("/user")
    public User userInfo(@RequestParam String id){
        User user=new User();
        user.setId(1l);
        user.setUsername("admin");
        user.setEmail("admin@example.com");
        user.setPhone("666666");
        return user;
    }

    @PostMapping("/user")
    public String saveUser(@RequestBody User user){
        return "SUCCESS";
    }

    @PutMapping("/user")
    public String updateUser(@RequestBody User user){
        return "SUCCESS";
    }

    @DeleteMapping("/user/{id}")
    public String deleteUser(@PathVariable("id") String id){
        return "SUCCESS";
    }

}