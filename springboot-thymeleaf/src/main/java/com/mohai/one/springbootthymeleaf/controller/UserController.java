package com.mohai.one.springbootthymeleaf.controller;

import com.mohai.one.springbootthymeleaf.bean.UserEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
public class UserController {

    @GetMapping(value = "/user")
    public ModelAndView test(HttpServletRequest req) {
        UserEntity user = new UserEntity();
        user.setRealName("逍遥子");
        user.setAge(28);
        user.setId(1);
        user.setBirthday(new Date());
        ModelAndView mv = new ModelAndView();
        mv.addObject("user", user);
        mv.setViewName("/user/info.html");
        return mv;
    }

}
