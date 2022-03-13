package com.mohai.one.springbootfreemarker.controller;

import com.mohai.one.springbootfreemarker.bean.UserEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class UserController {

    @GetMapping(value = "/userList")
    public String userList(Model model) {
        UserEntity user = new UserEntity();
        user.setRealName("逍遥子");
        user.setAge(28);
        user.setId(1);
        user.setBirthday(new Date());
        List< UserEntity> userList = new ArrayList<>();
        userList.add(user);
        model.addAttribute("userList",userList);
        return "/user/list";
    }

}
