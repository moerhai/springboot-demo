package com.mohai.one.springbootscheduled.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 多线程异步任务
 * @Auther: moerhai@qq.com
 * @Date: 2020/8/23 00:27
 */
@Component
@EnableAsync //开启多线程
public class AsyncThreadScheduleTask {

    @Async //异步执行
    //@Scheduled(fixedDelay = 1000)  //间隔1秒
    public void first() throws InterruptedException {
        System.out.println("线程 : ["+Thread.currentThread().getName()+"] 第一个异步任务开始 : " + LocalDateTime.now().toLocalTime());
        Thread.sleep(1000 * 5);
    }

    @Async //异步执行
   // @Scheduled(fixedRate = 2000) //间隔2秒
    public void second() {
        System.out.println("线程 : ["+Thread.currentThread().getName()+"] 第二个异步任务开始 : " + LocalDateTime.now().toLocalTime());
    }

}
