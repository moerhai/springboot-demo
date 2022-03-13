package com.mohai.one.springbootscheduledquartz.config;

import com.mohai.one.springbootscheduledquartz.service.MyScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/8/23 00:22
 */
@Configuration
public class QuartzSchedulingConfig implements ApplicationRunner {

    @Autowired
    private MyScheduler myScheduler;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        myScheduler.execute();
    }
}