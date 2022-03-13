package com.mohai.one.springbooterrorview.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 返回页面
     * @param e
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    public ModelAndView handle(RuntimeException e){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("errorPage");
        modelAndView.addObject("code", 500);
        modelAndView.addObject("msg", e.getMessage());
        return modelAndView;
    }

    /**
     * 返回JSON
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(MyException.class)
    public Map<String,Object> handleMyException(MyException e){
        Map<String,Object> map = new HashMap<>();
        map.put("code",e.getCode());
        map.put("message",e.getMsg());
        return map;
    }

}
