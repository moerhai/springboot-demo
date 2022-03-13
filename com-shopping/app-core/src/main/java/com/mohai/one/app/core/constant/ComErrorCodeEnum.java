package com.mohai.one.app.core.constant;

/**
 *  通用的错误码返回
 * @Auther: moerhai@qq.com
 * @Date: 2020/11/24 00:46
 */
public enum ComErrorCodeEnum {

    /* 用户登录错误信息 */
    USER_NOT_LOGIN(10001, "用户未登录"),
    USER_ACCOUNT_EXPIRED(10002, "账号已过期"),
    USER_CREDENTIALS_ERROR(10003, "密码错误"),
    USER_CREDENTIALS_EXPIRED(10004, "密码过期"),
    USER_ACCOUNT_DISABLE(10005, "账号不可用"),
    USER_ACCOUNT_LOCKED(10006, "账号被锁定"),
    USER_ACCOUNT_NOT_EXIST(10007, "账号不存在"),
    USER_ACCOUNT_ALREADY_EXIST(10008, "账号已存在"),
    USER_ACCOUNT_USE_BY_OTHERS(10009, "账号被迫下线"),
    NO_PERMISSION(10000, "没有权限"),
    UNKNOWN_ERROR(99999,"系统异常，请联系管理员");

    private Integer code;
    private String desc;

    ComErrorCodeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     *
     * @param code
     * @return
     */
    public static String getDescByCode(Integer code) {
        for (ComErrorCodeEnum comErrorCodeEnum : values()) {
            if (comErrorCodeEnum.code.equals(code)) {
                return comErrorCodeEnum.desc;
            }
        }
        return null;
    }


    public static void main(String[] args) {
        System.out.println(getDescByCode(2007));
    }

}