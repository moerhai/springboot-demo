package com.mohai.one.app.core.dto;

import com.github.pagehelper.Page;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/11/22 15:00
 */
public class ResultDto<T> {

    /**
     * 状态码
     */
    private int code;
    /**
     * 错误信息
     */
    private String message;
    /**
     * 数据
     */
    private T data;

    /**
     * 数据总数
     */
    private long total;

    public ResultDto(){
        this.code=0;
        this.message="SUCCESS";
    }

    public ResultDto(int code,String message){
        this.code=code;
        this.message=message;
    }

    public ResultDto(T data){
        if(data instanceof Page) {
            Page<T> page = (Page<T>) data;
            this.total = page.getTotal();
        }
        this.data = data;
    }

    public static ResultDto ok(){
        return new ResultDto();
    }

    public static ResultDto ok(Object data){
        return new ResultDto(data);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

}