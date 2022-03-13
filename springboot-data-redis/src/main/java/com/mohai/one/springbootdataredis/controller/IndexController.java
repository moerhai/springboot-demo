package com.mohai.one.springbootdataredis.controller;

import com.mohai.one.springbootdataredis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/4/4 0:04
 */
@RestController
public class IndexController {

    @Autowired
    private RedisService redisService;

    @RequestMapping("/index")
    public Set index(){
        return redisService.index();
    }

    @RequestMapping("/set")
    public String set(@RequestParam("key") String key,@RequestParam("value") String value){
        redisService.set(key,value);
        return "SUCCESS";
    }

    @RequestMapping("/get/{key}")
    public String get(@PathVariable String key){
        return redisService.get(key);
    }

    @RequestMapping("/delete")
    public String delete(String key){
        if(redisService.clearKey(key)){
            return "SUCCESS";
        }else{
            return "FAIL";
        }
    }

}