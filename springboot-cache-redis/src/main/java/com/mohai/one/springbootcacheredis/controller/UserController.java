package com.mohai.one.springbootcacheredis.controller;

import com.mohai.one.springbootcacheredis.domain.UserDTO;
import com.mohai.one.springbootcacheredis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/find/all")
    public List<UserDTO> findAll(){
        UserDTO userDTO = new UserDTO();
        Example<UserDTO> example = Example.of(userDTO);
        return userService.findList(example);
    }

}