package com.mohai.one.springbootscheduled.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 基于注解实现定时任务
 *
 * @Auther: moerhai@qq.com
 * @Date: 2020/8/23 00:20
 */
//@Component
public class StaticScheduleTask {

    private Logger log = LoggerFactory.getLogger(StaticScheduleTask.class);

    //cron表达式，表示每5秒执行一次
    @Scheduled(cron = "0/5 * * * * ?")
    private void testTasks() {
        log.info("testTasks===》执行定时任务时间: " + LocalDateTime.now());
    }

    //或者是指定时间间隔，例如：5秒，但间隔时间为6000
    @Scheduled(fixedRate = 5000)
    private void testTasks2() {
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("testTasks2===》执行定时任务时间: " + LocalDateTime.now());
    }

    //再者是指定固定时间间隔，此处执行时间间隔为5000+6000
    @Scheduled(fixedDelay = 5000)
    private void testTasks3() {
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("testTasks3===》执行定时任务时间: " + LocalDateTime.now());
    }

    //再者是指定固定时间间隔，此处会开启两个定时任务来执行testTasks4方法
    @Schedules({
            @Scheduled(fixedDelay = 5000),
            @Scheduled(fixedRate = 6000)}
    )
    private void testTasks4() {
        log.info("testTasks4===》执行定时任务时间: " + LocalDateTime.now());
    }
}
