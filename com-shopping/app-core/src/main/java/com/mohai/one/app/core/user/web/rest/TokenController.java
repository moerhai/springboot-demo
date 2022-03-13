package com.mohai.one.app.core.user.web.rest;

import com.mohai.one.app.core.user.service.AuthUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 刷新token
 * @Auther: moerhai@qq.com
 * @Date: 2020/12/18 00:01
 */
@RestController
@RequestMapping("/api/token")
public class TokenController {

    private static final String ACCESS_TOKEN = "access_token";

    private static final String REFRESH_TOKEN = "refresh_token";

    @Autowired
    private AuthUserService authUserService;

    /**
     * 刷新token
     * @param request
     * @return
     */
    @ApiOperation(value = "刷新token")
    @RequestMapping(value = "/refreshToken", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> refreshToken(HttpServletRequest request) {
        Map<String, String> tokenMap = authUserService.refreshToken(request);
        return ResponseEntity.ok(tokenMap);
    }

    /**
     * 删除token
     * @param request
     */
    @ApiOperation(value = "删除token")
    @DeleteMapping("/remove")
    public ResponseEntity<Object> removeToken(HttpServletRequest request){

        return ResponseEntity.ok().build();
    }

}
