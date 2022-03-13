package com.mohai.one.springbootscheduled.config;

import com.mohai.one.springbootscheduled.service.jdk.MyExecutorTask;
import com.mohai.one.springbootscheduled.service.jdk.MyTriggerTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/8/25 01:09
 */
//@Component
public class TimerMangerRunner implements ApplicationRunner {

    @Autowired
    private MyExecutorTask myExecutorTask;

    @Autowired
    private MyTriggerTask myTriggerTask;

    @Override
    public void run(ApplicationArguments args) throws Exception {
      // myExecutorTask.execute();
        myTriggerTask.execute();
        System.out.println("执行任务开始！！！");
    }
}
