package com.mohai.one.app.wares.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/11/20 21:21
 */
@RestController
public class IndexController {

    @Autowired
    private RequestMappingHandlerMapping handlerMapping;

    @RequestMapping("/index")
    public String index(){
        return "Hello World!";
    }


    @RequestMapping("/allApi")
    public List<String> getAllApi(HttpServletRequest request) {
        List<String> urlList = new ArrayList<>();
        Map<RequestMappingInfo, HandlerMethod> map = handlerMapping.getHandlerMethods();
        Set<RequestMappingInfo> set = map.keySet();
        for (RequestMappingInfo info : set) {
            HandlerMethod handlerMethod = map.get(info);
            // springmvc的url地址，不包含项目名
            PatternsRequestCondition patternsCondition = info.getPatternsCondition();
            System.out.println(patternsCondition);
            urlList.add(patternsCondition.getPatterns().iterator().next());
        }
        return urlList;
    }

}