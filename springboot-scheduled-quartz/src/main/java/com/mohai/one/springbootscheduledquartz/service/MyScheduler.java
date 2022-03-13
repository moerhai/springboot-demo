package com.mohai.one.springbootscheduledquartz.service;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Component;

import static java.util.concurrent.TimeUnit.MINUTES;
import static org.quartz.DateBuilder.futureDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.TriggerKey.triggerKey;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/8/30 17:58
 */
@Component
public class MyScheduler {

    public void execute() throws InterruptedException,SchedulerException{
        //创建调度器Scheduler
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        //创建JobDetail实例，并与 MyJob 类绑定（Job执行内容）
        JobDetail jobDetail = newJob(MyJob.class)
                .withIdentity("job", "group").build();
        //构建Trigger实例，每隔5s执行一次
        Trigger trigger = newTrigger().withIdentity("trigger", "triggerGroup")
                .startNow()//立即生效
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(5)//每隔5s执行一次
                        .repeatForever()).build();//循环执行
        // 给jobDetail任务添加trigger触发器
        scheduler.scheduleJob(jobDetail, trigger);
        System.out.println("--------myJob scheduler start ! ------------");
        // 启动调度
        scheduler.start();
        // 睡眠执行一分钟
        MINUTES.sleep(1);
        // 在执行一分钟后停止
        scheduler.shutdown();
        System.out.println("--------myJob scheduler shutdown ! ------------");

    }

//    public void execute2()throws SchedulerException{
//
//        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
//        Scheduler scheduler = schedulerFactory.getScheduler();
//
//        JobDetail job = newJob(MyJob.class)
//                 .withIdentity("myJob")
//                 .build();
//
//        Trigger trigger = newTrigger()
//                  .withIdentity(triggerKey("myTrigger", "myTriggerGroup"))
//                    .withSchedule(simpleSchedule()
//                       .withIntervalInHours(1)
//                    .repeatForever())
//           .startAt(futureDate(10, MINUTES))
//                  .build();
//
//         scheduler.scheduleJob(job, trigger);
//    }

}