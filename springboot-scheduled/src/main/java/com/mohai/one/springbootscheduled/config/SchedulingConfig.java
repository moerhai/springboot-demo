package com.mohai.one.springbootscheduled.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
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
public class SchedulingConfig implements SchedulingConfigurer {

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        //自定义线程池
         //taskRegistrar.setScheduler(taskExecutor());
    }

    //注入一个长度为5的定时任务线程池
//    @Bean(destroyMethod="shutdown")
//    public Executor taskExecutor() {
//        return Executors.newScheduledThreadPool(5 ,new ThreadFactory() {
//            private final AtomicLong counter = new AtomicLong();
//            @Override
//            public Thread newThread(Runnable r) {
//                Thread thread = new Thread(r);
//                thread.setName("my-scheduler-" + counter.incrementAndGet());
//                return thread;
//            }
//        });
//    }

    // 创建定时任务线程池
  //  @Bean
//    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
//        ThreadPoolTaskScheduler executor = new ThreadPoolTaskScheduler();
//        // 核心数为10
//        executor.setPoolSize(10);
//        // 线程名前缀 taskExecutor-
//        executor.setThreadNamePrefix("taskExecutor-");
//        // 当调度器shutdown被调用时等待当前被调度的任务完成
//        executor.setWaitForTasksToCompleteOnShutdown(true);
//        // 等待时常
//        executor.setAwaitTerminationSeconds(60);
//        // 设置当任务被取消的同时从当前调度器移除的策略
//        executor.setRemoveOnCancelPolicy(true);
//        return executor;
//    }

}