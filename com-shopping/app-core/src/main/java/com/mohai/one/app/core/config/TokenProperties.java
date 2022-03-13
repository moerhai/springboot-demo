package com.mohai.one.app.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/12/14 00:38
 */
@ConfigurationProperties(prefix = "jwt.token")
public class TokenProperties {

    /**
     * Request Headers: Authorization
     */
    private String header;
    /**
     * The token prefix 'Bearer '
     */
    private String tokenStartWith;
    /**
     * The token expiration time
     */
    private Long expiration;
    /**
     * The token must be encoded with a minimum of 88 bits Base64
     */
    private String base64Secret;

    private String onlineKey;

    private String codeKey;

    /**
     * oauth2 相关前缀
     */
    private String oauthPrefix;

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getTokenStartWith() {
        return tokenStartWith;
    }

    public String getTokenStartWithSpace(){
        return tokenStartWith + " ";
    }

    public void setTokenStartWith(String tokenStartWith) {
        this.tokenStartWith = tokenStartWith;
    }

    public Long getExpiration() {
        return expiration;
    }

    public void setExpiration(Long expiration) {
        this.expiration = expiration;
    }

    public String getBase64Secret() {
        return base64Secret;
    }

    public void setBase64Secret(String base64Secret) {
        this.base64Secret = base64Secret;
    }

    public String getOnlineKey() {
        return onlineKey;
    }

    public void setOnlineKey(String onlineKey) {
        this.onlineKey = onlineKey;
    }

    public String getCodeKey() {
        return codeKey;
    }

    public void setCodeKey(String codeKey) {
        this.codeKey = codeKey;
    }

    public String getOauthPrefix() {
        return oauthPrefix;
    }

    public void setOauthPrefix(String oauthPrefix) {
        this.oauthPrefix = oauthPrefix;
    }
}