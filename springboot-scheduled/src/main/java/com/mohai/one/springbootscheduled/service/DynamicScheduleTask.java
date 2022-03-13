package com.mohai.one.springbootscheduled.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 基于 SchedulingConfigurer 接口实现定时任务
 * @Auther: moerhai@qq.com
 * @Date: 2020/8/23 00:21
 */
@Slf4j
//@Component
public class DynamicScheduleTask implements SchedulingConfigurer {

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {


        taskRegistrar.addTriggerTask(
                //1.添加任务内容(Runnable)
                () -> log.info("执行动态定时任务时间: " + LocalDateTime.now().toLocalTime()),
                //2.设置执行周期(Trigger)
                triggerContext -> {
                    String cron = "0/6 * * * * ?";
                    return new CronTrigger(cron).nextExecutionTime(triggerContext);
                }
        );

    }

}