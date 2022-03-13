package com.mohai.one.app.core.exception;

/**
 * 业务异常
 * @Auther: moerhai@qq.com
 * @Date: 2020/11/27 01:55
 */
public class BusinessException extends RuntimeException{

    public BusinessException(String msg){
        super(msg);
    }

    public BusinessException(String msg,Throwable cause) {
        super(msg,cause);
    }
}