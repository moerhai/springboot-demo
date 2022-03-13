package com.mohai.one.app.core.user.web.rest;

import com.mohai.one.app.core.user.service.AuthUserService;
import com.mohai.one.app.core.user.web.vo.AuthUserVo;
import com.mohai.one.app.core.user.web.vo.OnlineUserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 系统登录授权
 * @Auther: moerhai@qq.com
 * @Date: 2020/11/25 01:11
 */
@Api(tags = "系统登录授权接口")
@RestController
@RequestMapping("/api/auth")
public class AuthUserController {

    @Autowired
    private AuthUserService authUserService;

    /**
     * 用户登录
     * @param authUser
     * @return
     */
    @ApiOperation("用户登录授权")
    @PostMapping(value = "/login")
    public ResponseEntity<Object> login(@Validated @RequestBody AuthUserVo authUser,HttpServletRequest request){
        Map<String,Object> authInfo = authUserService.login(authUser,request);
        return ResponseEntity.ok(authInfo);
    }

    /**
     * 获取验证码
     * @return
     */
    @ApiOperation("获取验证码")
    @GetMapping(value = "/code")
    public ResponseEntity<Object> getCode(){
        Map<String,Object> imgResult = authUserService.createCode();
        return ResponseEntity.ok(imgResult);
    }

    /**
     * 获取当前用户信息
     * @param request
     * @return
     */
    @ApiOperation("获取当前用户信息")
    @GetMapping(value = "/info")
    public ResponseEntity<OnlineUserVo> getUserInfo(HttpServletRequest request){
        OnlineUserVo onlineUserVo = authUserService.getUserInfo(request);
        return ResponseEntity.ok(onlineUserVo);
    }

//    @ApiOperation(value = "用户注册")
//    @RequestMapping(value = "/register", method = RequestMethod.POST)
//    @ResponseBody
//    public ResponseEntity<Object> register(@Validated @RequestBody RegisterUserVo registerUserVo) {
//        authUserService.register(registerUserVo);
//        return ResponseEntity.ok().build();
//    }

    /**
     * 用户退出
     * @param request
     * @return
     */
    @ApiOperation("用户退出登录")
    @DeleteMapping(value = "/logout")
    public ResponseEntity<Object> logout(HttpServletRequest request){
        authUserService.logout(request);
        return ResponseEntity.ok().build();
    }

}