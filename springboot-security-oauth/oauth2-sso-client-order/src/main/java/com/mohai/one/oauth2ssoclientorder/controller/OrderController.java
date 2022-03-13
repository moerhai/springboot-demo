package com.mohai.one.oauth2ssoclientorder.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/10/7 23:23
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    @RequestMapping("/list")
    public String list(){

        return "SUCCESS";
    }


    @GetMapping("/info")
    @ResponseBody
    public Principal info(Principal principal) {
        return principal;
    }

    @GetMapping("/me")
    @ResponseBody
    public Authentication me(Authentication authentication) {
        return authentication;
    }

    @PreAuthorize("hasAuthority('member:save')")
    @ResponseBody
    @PostMapping("/add")
    public String add() {

        return "add";
    }

    @PreAuthorize("hasAuthority('member:detail')")
    @ResponseBody
    @GetMapping("/detail")
    public String detail() {
        return "detail";
    }

}
