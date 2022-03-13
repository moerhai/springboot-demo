package com.mohai.one.springbootactivemq.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/10/4 11:18
 */
public class Message implements Serializable {

    private String title; //消息标题
    private String content; //消息的主体
    private byte status; //消息状态

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Message{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", status=" + status +
                '}';
    }
}