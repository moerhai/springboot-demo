package com.mohai.one.springbootcache.controller;

import com.mohai.one.springbootcache.domain.UserDTO;
import com.mohai.one.springbootcache.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/8/1 22:06
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    /**
     * 测试@Cacheable
     * @param id
     * @return
     */
    @GetMapping("/get/{id}")
    public UserDTO get(@PathVariable String id){
        UserDTO userDTO = userService.findById(Long.valueOf(id));
        return userDTO;
    }

    /**
     * 测试 @CachePut
     * @param userDTO
     * @return
     */
    @PutMapping("/save")
    public UserDTO save(@RequestBody UserDTO userDTO){
       return userService.save(userDTO);
    }

    /**
     * 测试@CacheEvict
     * @param id
     * @return
     */
    @DeleteMapping(value = "/delete/{id}",produces = {" text/html;charset=UTF-8"})
    public String delete(@PathVariable String id){
        userService.delete(Long.valueOf(id));
        return "SUCCESS";
    }

    /**
     * 测试@Caching
     * @param name
     * @return
     */
    @GetMapping("/find")
    public UserDTO find(@RequestParam("name") String name){
        UserDTO userDTO = userService.getUserByName(name);
        return userDTO;
    }

}