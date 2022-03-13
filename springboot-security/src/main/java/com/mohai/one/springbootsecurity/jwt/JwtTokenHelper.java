//package com.mohai.one.springbootsecurity.jwt;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// *
// * @Auther: moerhai@qq.com
// * @Date: 2020/9/20 10:03
// */
//@Component
//public class JwtTokenHelper {
//
//    private static Logger logger = LoggerFactory.getLogger(JwtTokenHelper.class);
//
//
//    /**
//     * 用户id
//     */
//    private static final String CLAIM_KEY_USERNAME = "sub";
//
//    /**
//     * 用户登录信息
//     */
//    private static final String AUTHORITY_USER_DETAIL = "detail";
//
//    /**
//     * token创建时间
//     */
//    private static final String CLAIM_KEY_CREATED = "created";
//
//    @Value("${jwt.secret}")
//    private String secret;
//
//    @Value("${jwt.expiration.pc.access}")
//    private Long pcAccessExpiration;
//
//    @Value("${jwt.expiration.pc.refresh}")
//    private Long pcRefreshExpiration;
//
//    @Value("${jwt.expiration.wechat.access}")
//    private Long weChatAccessExpiration;
//
//    @Value("${jwt.expiration.wechat.refresh}")
//    private Long weChatRefreshExpiration;
//
//    /**
//     * 获取用户token
//     *
//     * @param token
//     * @return
//     */
//    public String getUsernameFromToken(String token) {
//        String username;
//        try {
//            final Claims claims = getClaimsFromToken(token);
//            username = claims.getSubject();
//        } catch (Exception e) {
//            username = null;
//        }
//        return username;
//    }
//
//    /**
//     * 获取用户token
//     *
//     * @param token
//     * @return
//     */
//    public AuthorityUserDto getUserDetailFromToken(String token) {
//        AuthorityUserDto detail;
//        try {
//            final Claims claims = getClaimsFromToken(token);
//            Object  detailObject = claims.get(AUTHORITY_USER_DETAIL);
//            Gson gson = new Gson();
//            // 解析json
//            detail = gson.fromJson(gson.toJson(detailObject), AuthorityUserDto.class);
//        } catch (Exception e) {
//            detail = null;
//        }
//        return detail;
//    }
//
//    /**
//     * 获取token的创建时间
//     *
//     * @param token
//     * @return
//     */
//    public Date getCreatedDateFromToken(String token) {
//        Date created;
//        try {
//            final Claims claims = getClaimsFromToken(token);
//            created = new Date((Long) claims.get(CLAIM_KEY_CREATED));
//        } catch (Exception e) {
//            created = null;
//        }
//        return created;
//    }
//
//    /**
//     * 获取token的过期时间
//     *
//     * @param token
//     * @return
//     */
//    public Date getExpirationDateFromToken(String token) {
//        Date expiration;
//        try {
//            final Claims claims = getClaimsFromToken(token);
//            expiration = claims.getExpiration();
//        } catch (Exception e) {
//            expiration = null;
//        }
//        return expiration;
//    }
//
//    /**
//     * 调用jar生成token令牌
//     *
//     * @param token
//     * @return
//     */
//    private Claims getClaimsFromToken(String token) {
//        Claims claims;
//        try {
//            claims = Jwts.parser()
//                    .setSigningKey(secret)
//                    .parseClaimsJws(token)
//                    .getBody();
//        } catch (Exception e) {
//            logger.error("解析token是失败：错误信息：{}", e);
//            claims = null;
//        }
//        return claims;
//    }
//
//    /**
//     * 生成过期时间
//     *
//     * @param tokenExpirationDto
//     * @return
//     */
//    private Date generateExpirationDate(TokenExpirationDto tokenExpirationDto) {
//        Date expirationDate = null;
//        ChannelEnum channelEnum = tokenExpirationDto.getChannelEnum();
//        TokenEnum tokenEnum = tokenExpirationDto.getTokenEnum();
//        if (channelEnum.getType() == ChannelEnum.PC_CHANNEL.getType()) {
//            if (tokenEnum.getType() == TokenEnum.ACCESS_TOKEN.getType()) {
//                expirationDate = new Date(System.currentTimeMillis() + pcAccessExpiration * 1000);
//            } else {
//                expirationDate = new Date(System.currentTimeMillis() + pcRefreshExpiration * 1000);
//            }
//        } else {
//            if (tokenEnum.getType() == TokenEnum.ACCESS_TOKEN.getType()) {
//                expirationDate = new Date(System.currentTimeMillis() + weChatAccessExpiration * 1000);
//            } else {
//                expirationDate = new Date(System.currentTimeMillis() + weChatRefreshExpiration * 1000);
//            }
//        }
//        return expirationDate;
//    }
//
//    /**
//     * 校验token是否过期
//     *
//     * @param token
//     * @return
//     */
//    private Boolean isTokenExpired(String token) {
//        final Date expiration = getExpirationDateFromToken(token);
//        return expiration.before(new Date());
//    }
//
//    /**
//     * 生成token
//     *
//     * @param userDetails
//     * @param tokenExpirationDto
//     * @return
//     */
//    public String generateToken(UserDetails userDetails, TokenExpirationDto tokenExpirationDto, AuthorityUserDto authorityUserDto) {
//        Map<String, Object> claims = new HashMap<>();
//        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
//        claims.put(CLAIM_KEY_CREATED, new Date());
//        claims.put(AUTHORITY_USER_DETAIL, authorityUserDto);
//        return generateToken(claims, tokenExpirationDto);
//    }
//
//    /**
//     * 生成token
//     *
//     * @param claims
//     * @param tokenExpirationDto
//     * @return
//     */
//    String generateToken(Map<String, Object> claims, TokenExpirationDto tokenExpirationDto) {
//        return Jwts.builder()
//                .setClaims(claims)
//                .setExpiration(generateExpirationDate(tokenExpirationDto))
//                .signWith(SignatureAlgorithm.HS512, secret)
//                .compact();
//    }
//
//    /**
//     * 是否具备刷新条件
//     *
//     * @param token
//     * @return
//     */
//    public Boolean canTokenBeRefreshed(String token) {
//        return !isTokenExpired(token);
//    }
//
//    /**
//     * 刷新token
//     *
//     * @param refreshAuthorityQuery
//     * @param tokenExpirationDto
//     * @return
//     */
//    public String refreshToken(RefreshAuthorityQuery refreshAuthorityQuery, TokenExpirationDto tokenExpirationDto) {
//        String refreshedToken;
//        try {
//            final Claims claims = getClaimsFromToken(refreshAuthorityQuery.getToken());
//            claims.put(CLAIM_KEY_CREATED, new Date());
//            refreshedToken = generateToken(claims, tokenExpirationDto);
//        } catch (Exception e) {
//            refreshedToken = null;
//        }
//        return refreshedToken;
//    }
//
//    /**
//     * 校验token是否合法
//     *
//     * @param token
//     * @param userDetails
//     * @return
//     */
//    public Boolean validateToken(String token, UserDetails userDetails) {
//        final String username = getUsernameFromToken(token);
//        final Date created = getCreatedDateFromToken(token);
//        return !isTokenExpired(token);
//    }
//
//}
