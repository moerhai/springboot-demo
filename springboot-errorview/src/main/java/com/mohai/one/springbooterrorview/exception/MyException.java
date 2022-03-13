package com.mohai.one.springbooterrorview.exception;

public class MyException extends RuntimeException {

    private int code;
    private String msg;

    public MyException(String msg){
        super(msg);
        this.code=500;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
