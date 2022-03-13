package com.mohai.one.app.core.token;

import cn.hutool.core.date.DateUtil;
import com.mohai.one.app.core.config.TokenProperties;
import com.mohai.one.app.core.exception.BadRequestException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

/**
 *  JwtToken生成的工具类
 *  token的格式：header.payload.signature
 *  header的格式（算法、token的类型）：
 *  {"alg": "HS512","typ": "JWT"}
 *
 * @Auther: moerhai@qq.com
 * @Date: 2020/11/22 01:14
 */
@Component
public class JwtTokenProvider implements InitializingBean {

    private static final Logger LOG = LoggerFactory.getLogger(JwtTokenProvider.class);
    /**
     * 用户id
     */
    private static final String CLAIM_KEY_USER_ID = "jti";
    /**
     * 用户名
     */
    private static final String CLAIM_KEY_USERNAME = "sub";
    /**
     * 用户登录授权信息
     */
    private static final String AUTHORITY_USER_DETAIL = "auth";

    private Key key;

    private final TokenProperties tokenProperties;

    public JwtTokenProvider(TokenProperties tokenProperties) {
        this.tokenProperties = tokenProperties;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(tokenProperties.getBase64Secret());
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims getClaimsFromToken(String token){
        try {
             Claims claims = Jwts.parserBuilder() // 创建解析对象
                    .setSigningKey(key)  // 设置安全密钥
                    .build()
                    .parseClaimsJws(token) // 解析token
                    .getBody(); // 获取 payload 部分内容
            return claims;
        } catch (Exception e) {
            LOG.error("JWT格式验证失败:{}",token);
            throw new BadRequestException("登录超时，请重新登录。");
        }
    }

    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .compressWith(CompressionCodecs.DEFLATE) //采用默认的压缩方式
                .setClaims(claims)
                .signWith(key, SignatureAlgorithm.HS512)
                .setIssuedAt(new Date()) //签发时间
                .setExpiration(generateExpirationDate())
                .compact();
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + tokenProperties.getExpiration());
    }

    /**
     * 创建token
     * @param authentication 认证信息
     * @param userId  用户id
     * @return
     */
    public String createToken(Authentication authentication,String userId) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Map<String,Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME,authentication.getName());
        claims.put(AUTHORITY_USER_DETAIL,authorities);
        claims.put(CLAIM_KEY_USER_ID,userId);

        return generateToken(claims);
    }

    /**
     * 校验token是否正常
     * @param authToken
     * @return
     */
    public boolean validateToken(String authToken) {
        try {
            LOG.debug("正在解析Token [{}]",authToken);
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(authToken);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            LOG.error("Invalid JWT signature.");
        } catch (ExpiredJwtException e) {
            LOG.error("Expired JWT token.");
        } catch (UnsupportedJwtException e) {
            LOG.error("Unsupported JWT token.");
        } catch (JwtException e){
            LOG.error("Can't use the JWT as intended by its creator.");
        } catch (IllegalArgumentException e) {
            LOG.error("JWT token compact of handler are invalid.");
        }
        return false;
    }

    /**
     * 从请求头中获取token
     * @param request
     * @return
     */
    public String getToken(HttpServletRequest request){
        String requestHeader = request.getHeader(tokenProperties.getHeader());
        if (requestHeader != null && requestHeader.startsWith(tokenProperties.getTokenStartWith()) && requestHeader.length() > tokenProperties.getTokenStartWith().length() + 1) {
            return requestHeader.substring(tokenProperties.getTokenStartWith().length() + 1);
        }
        return null;
    }

    /**
     * 从token中获取登录用户名
     * @param token
     * @return
     */
    public String getUserNameFromToken(String token) {
        String username;
        try {
            final Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            LOG.error("JWT获取用户名失败:{}",token);
            username = null;
        }
        return username;
    }

    /**
     * 获取token创建时间
     * @param token
     * @return
     */
    public Date getCreatedDateFromToken(String token) {
        Date created;
        try {
            final Claims claims = getClaimsFromToken(token);
            created = claims.getIssuedAt();
        } catch (Exception e) {
            LOG.error("JWT获取创建时间失败:{}",token);
            created = null;
        }
        return created;
    }

    /**
     * 获取当前token的失效时间
     * @param token
     * @return
     */
    public Date getExpirationDateFromToken(String token){
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            LOG.error("JWT获取过期时间失败:{}",token);
            expiration = null;
        }
        return expiration;
    }

    /**
     * 根据当前时间计算过期时间
     * @param createdDate
     * @return
     */
    public Date calculateExpirationDate(Date createdDate) {
        return new Date(createdDate.getTime() + tokenProperties.getExpiration());
    }

    /**
     * 验证token是否还有效
     *
     * @param token       客户端传入的token
     * @param userDetails 从数据库中查询出来的用户信息
     * @return  返回 true表示token有效
     */
    public boolean validateTokenExpired(String token, UserDetails userDetails) {
        final String username = getUserNameFromToken(token);
        return userDetails.getUsername().equals(username) && !isTokenExpired(token);
    }

    /**
     * 判断token是否已经失效
     * @param token
     * @return  返回 true表示已失效
     */
    private boolean isTokenExpired(String token) {
        final Date expiredDate = getExpirationDateFromToken(token);
        return expiredDate.before(new Date());
    }

    /**
     * 当原来的token没过期时是可以刷新的
     *
     * @param oldToken
     */
    public String refreshHeadToken(String oldToken) {
        String refreshedToken;
        try{
            if(StringUtils.isEmpty(oldToken)){
                refreshedToken = null;
            }
            //如果token已经过期，不支持刷新
            if(isTokenExpired(oldToken)){
                refreshedToken = null;
            }
            //如果token在30分钟之内刚刷新过，返回原token
            if(tokenRefreshJustBefore(oldToken,30*60)){
                refreshedToken = oldToken;
            }else{
                //否则新生成一个token
                final Claims claims = getClaimsFromToken(oldToken);
                refreshedToken = generateToken(claims);
            }
        }catch (Exception e){
            LOG.error("刷新token失败:{}",oldToken);
            refreshedToken = null;
        }
        return refreshedToken;
    }
    /**
     * 判断token在指定时间内是否刚刚刷新过
     * @param token 原token
     * @param time 指定时间（秒）
     */
    private boolean tokenRefreshJustBefore(String token, int time) {
        Date created = getCreatedDateFromToken(token);
        Date refreshDate = new Date();
        //刷新时间在创建时间的指定时间内
        if(refreshDate.after(created)&&refreshDate.before(DateUtil.offsetSecond(created,time))){
            return true;
        }
        return false;
    }

}