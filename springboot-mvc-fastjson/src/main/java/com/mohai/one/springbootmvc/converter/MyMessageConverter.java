package com.mohai.one.springbootmvc.converter;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;


public class MyMessageConverter extends AbstractHttpMessageConverter {

    public MyMessageConverter() {
        super(new MediaType("application", "json", Charset.forName("UTF-8")));
    }

    @Override
    protected boolean supports(Class clazz) {
        return true;
    }

    /**
     * 读取请求数据
     * @param clazz
     * @param inputMessage
     * @return
     * @throws IOException
     * @throws HttpMessageNotReadableException
     */
    @Override
    protected Object readInternal(Class clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        String temp = StreamUtils.copyToString(inputMessage.getBody(), Charset.forName("UTF-8"));
        return temp;
    }

    /**
     * 处理输出数据
     * @param o
     * @param outputMessage
     * @throws IOException
     * @throws HttpMessageNotWritableException
     */
    @Override
    protected void writeInternal(Object o, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        StreamUtils.copy(o.toString(), Charset.forName("UTF-8"), outputMessage.getBody());
    }
}
