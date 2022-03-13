package com.mohai.one.app.core.holder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 获取当前请求头信息
 * @Auther: moerhai@qq.com
 * @Date: 2020/12/15 01:26
 */
public class CurrentHeadHolder {

    private static final Logger LOG = LoggerFactory.getLogger(CurrentHeadHolder.class);
    /**
     * 线程变量存储当前请求头信息
     */
    private ThreadLocal<Map> currentHead = new ThreadLocal<>();

    private CurrentHeadHolder(){}

    public static void initCurrentHeader(String sysCode){

    }


}
