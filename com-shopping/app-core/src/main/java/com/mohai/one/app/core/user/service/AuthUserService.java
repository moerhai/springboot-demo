package com.mohai.one.app.core.user.service;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.captcha.generator.MathGenerator;
import cn.hutool.core.util.IdUtil;
import com.mohai.one.app.core.config.TokenProperties;

import com.mohai.one.app.core.exception.BadRequestException;
import com.mohai.one.app.core.model.AdminSUser;
import com.mohai.one.app.core.redis.RedisUtil;

import com.mohai.one.app.core.token.JwtTokenProvider;
import com.mohai.one.app.core.user.web.vo.AuthUserVo;
import com.mohai.one.app.core.user.web.vo.OnlineUserVo;
import com.mohai.one.app.core.utils.UserAgentUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 用户登录、注册、退出逻辑实现
 * @Auther: moerhai@qq.com
 * @Date: 2020/11/26 00:10
 */
@Service
public class AuthUserService {

    private static final Logger LOG = LoggerFactory.getLogger(AuthUserService.class);

    @Value("${single.login:false}")
    private Boolean singleLogin;

    @Value("${rsa.private-key}")
    private String privateKey;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private TokenProperties tokenProperties;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 登录
     * @param authUserVo
     * @param request
     * @return
     */
    public Map<String,Object> login(AuthUserVo authUserVo, HttpServletRequest request){
        try{
            // 密码解密
//            RSACryptor rsaCryptor = new RSACryptor(privateKey,null);
//            String password = rsaCryptor.decrypt(RSACryptor.KeyType.PRIVATE_KEY,authUserVo.getPassword());

            // 对称解密
          //   RSA rsa = new RSA(privateKey, null);
          //  String password = new String(rsa.decrypt(authUserVo.getPassword(), KeyType.PrivateKey));

            // 查询验证码
            if(!StringUtils.isBlank(authUserVo.getUuId())){
                String code = (String) redisUtil.get(tokenProperties.getCodeKey() + authUserVo.getUuId());
                // 清除验证码
                redisUtil.del(tokenProperties.getCodeKey() + authUserVo.getUuId());
                if (StringUtils.isBlank(code)) {
                    throw new BadRequestException("验证码不存在或已过期");
                }
                //校验验证码
                MathGenerator mathGenerator = new MathGenerator();
                boolean flag = mathGenerator.verify(code,authUserVo.getCode());
                if (StringUtils.isBlank(authUserVo.getCode()) || !flag) {
                    throw new BadRequestException("验证码错误");
                }
            }
            // 构造UsernamePasswordAuthenticationToken
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(authUserVo.getUsername(), authUserVo.getPassword());

            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            final AdminSUser adminSUser = (AdminSUser) authentication.getPrincipal();
            // 生成令牌
            String token = jwtTokenProvider.createToken(authentication,adminSUser.getUserId());
            // 登录成功后，将信息保存到redis
            OnlineUserVo onlineUserVo = saveOnlineUser(adminSUser,token,request);
            // 返回 token 与 用户信息
            Map<String,Object> authInfo = new HashMap<String,Object>(2){{
                put("token", tokenProperties.getTokenStartWithSpace() + token);
                put("user", onlineUserVo);
            }};
            if(singleLogin){
                //踢掉之前已登录的token
                checkLoginOnUser(adminSUser.getUsername(),token);
            }
            return authInfo;
        }catch (AuthenticationException e) {
            LOG.error("登录异常:{}", e.getMessage());
            throw new BadRequestException(e.getMessage());
        }
    }

    /**
     * 向redis中保存在线用户信息
     * @param adminSUser
     * @param token
     * @param request
     */
    private OnlineUserVo saveOnlineUser(AdminSUser adminSUser, String token, HttpServletRequest request) {
        String ip = UserAgentUtils.getIp(request);
        String browser = UserAgentUtils.getBrowser(request);
        OnlineUserVo onlineUserVo = new OnlineUserVo(token,adminSUser.getUsername(),adminSUser.getNickName(),browser,ip,new Date());
        redisUtil.set(tokenProperties.getOnlineKey() + token, onlineUserVo,tokenProperties.getExpiration()/1000);
        return onlineUserVo;
    }

    private void checkLoginOnUser(String username, String token) {


    }

    /**
     * 用户退出
     * @param request
     */
    public void logout(HttpServletRequest request) {
        String token = jwtTokenProvider.getToken(request);
        if(!StringUtils.isBlank(token)){
            String key = tokenProperties.getOnlineKey() + token;
            redisUtil.del(key);
        }
        SecurityContextHolder.clearContext();
    }

    /**
     * 获取验证码
     * @return
     */
    public Map<String, Object> createCode() {
        // 定义圆圈干扰验证码的长和宽
        CircleCaptcha circleCaptcha = CaptchaUtil.createCircleCaptcha(111,36);
        // 算术类型
        MathGenerator mathGenerator = new MathGenerator();
        circleCaptcha.setGenerator(mathGenerator);
        // 生成验证码
        circleCaptcha.createCode();
        // 获取验证码
        String code = circleCaptcha.getCode();
        System.out.println(code);
        String uuid = IdUtil.simpleUUID();
        // 保存到redis,保留2分钟
        redisUtil.set(tokenProperties.getCodeKey() + uuid,code,2,TimeUnit.MINUTES);
        Map<String,Object> imgResult = new HashMap<String,Object>(2){{
            put("img","data:image/png;base64,"+circleCaptcha.getImageBase64());
            put("uuid", uuid);
        }};
        return imgResult;
    }

//    /**
//     * 用户注册
//     * @param registerUserVo
//     */
//    public void register(RegisterUserVo registerUserVo) {
//
//
//    }

    /**
     * 通过token获取用户信息
     * @param request
     * @return
     */
    public OnlineUserVo getUserInfo(HttpServletRequest request) {
        String token = jwtTokenProvider.getToken(request);
        Object obj = redisUtil.get(tokenProperties.getOnlineKey() + token);
       // OnlineUserVo onlineUserVo = JSONObject.parseObject(JSONObject.toJSONString(obj),OnlineUserVo.class);
        return (OnlineUserVo) obj;
    }

    /**
     * 刷新token
     * @param request
     * @return
     */
    public Map<String, String> refreshToken(HttpServletRequest request) {
        String token = jwtTokenProvider.getToken(request);
        String refreshToken = jwtTokenProvider.refreshHeadToken(token);
        if (refreshToken == null) {
            throw new BadRequestException("token已经过期！");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", tokenProperties.getTokenStartWithSpace() + refreshToken);
        return tokenMap;
    }

    /**
     * 删除token
     * @param request
     */
//    public void removeToken(HttpServletRequest request) {
//        String token = jwtTokenProvider.getToken(request);
//
//    }
}