package com.mohai.one.springbootscheduledquartz.service;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.time.LocalDateTime;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/8/30 23:03
 */
public class MyJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.err.println("任务正在执行... " + LocalDateTime.now());
    }
}
