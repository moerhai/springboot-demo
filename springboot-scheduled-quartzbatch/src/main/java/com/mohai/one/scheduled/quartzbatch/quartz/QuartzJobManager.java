package com.mohai.one.scheduled.quartzbatch.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 *
 * @Auther: moerhai@qq.com
 * @Date: 2020/9/6 15:01
 */
public class QuartzJobManager {

    private static final Logger LOG = LoggerFactory.getLogger(QuartzJobManager.class);

    //创建一个SchedulerFactory工厂实例
    private SchedulerFactory stdSchedulerFactory = new StdSchedulerFactory();
    //任务组名称
    private String JOB_GROUP_NAME = "MH_JOB_GROUP_NAME";
    //触发器组名称
    private String TRIGGER_GROUP_NAME = "MH_TRIGGER_GROUP_NAME";

    /**
     * 添加一个定时任务，使用默认的任务组名，触发器名，触发器组名
     * @param jobName 任务名
     * @param cls 任务
     * @param cronExpression cron表达式
     */
    public void addJob(String jobName, Class<? extends Job> cls, String cronExpression) {
        try {
            //通过SchedulerFactory构建Scheduler对象
            Scheduler scheduler = stdSchedulerFactory.getScheduler();
            //用于描叙Job实现类及其他的一些静态信息，构建一个作业实例
            JobDetail jobDetail= JobBuilder
                    .newJob(cls)
                    .withIdentity(jobName,JOB_GROUP_NAME)
                    .build();
            //TriggerBuilder创建一个新的触发器
            CronTrigger trigger = TriggerBuilder
                    .newTrigger()
                    //给触发器起一个名字和组名
                    .withIdentity(jobName, TRIGGER_GROUP_NAME)
                    .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                    .build();
            scheduler.scheduleJob(jobDetail, trigger);
            if (!scheduler.isShutdown()) {
                //启动调度
                scheduler.start();
            }
        } catch (Exception e) {
            LOG.error("任务添加失败！",e);
            throw new RuntimeException(e);
        }
    }

    /**
     *  添加一个定时任务，使用默认的任务组名，触发器名，触发器组名  （带参数）
     * @param jobName 任务名
     * @param cls 任务
     * @param cronExpression cron表达式
     */
    public void addJob(String jobName, Class<? extends Job> cls, String cronExpression, Map<String,Object> parameter) {
        try {
            //通过SchedulerFactory构建Scheduler对象
            Scheduler scheduler = stdSchedulerFactory.getScheduler();
            //用于描叙Job实现类及其他的一些静态信息，构建一个作业实例
            JobDetail jobDetail= JobBuilder
                    .newJob(cls)
                    .withIdentity(jobName,JOB_GROUP_NAME)
                    .build();
            jobDetail.getJobDataMap().putAll(parameter);
            //TriggerBuilder创建一个新的触发器
            CronTrigger trigger = TriggerBuilder
                    .newTrigger()
                    //给触发器起一个名字和组名
                    .withIdentity(jobName, TRIGGER_GROUP_NAME)
                    .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                    .build();
            scheduler.scheduleJob(jobDetail, trigger);
            if (!scheduler.isShutdown()) {
                //启动调度
                scheduler.start();
            }
        } catch (Exception e) {
            LOG.error("任务添加失败！",e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 移除一个任务(使用默认的任务组名，触发器名，触发器组名)
     * @param jobName    任务名称
     */
    public void removeJob(String jobName) {
        try {
            Scheduler scheduler = stdSchedulerFactory.getScheduler();
            // 通过任务名和组名获取JobKey
            JobKey jobKey = JobKey.jobKey(jobName, JOB_GROUP_NAME);
            // 通过触发器名和组名获取TriggerKey
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName,TRIGGER_GROUP_NAME);
            // 停止触发器
            scheduler.pauseTrigger(triggerKey);
            // 移除触发器
            scheduler.unscheduleJob(triggerKey);
            // 删除任务
            scheduler.deleteJob(jobKey);
        } catch (Exception e) {
            LOG.error("移除任务失败！",e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 启动所有定时任务
     */
    public void startAllJobs() {
        try {
            Scheduler scheduler = stdSchedulerFactory.getScheduler();
            scheduler.start();
        } catch (Exception e) {
            LOG.error("启动所有任务失败！",e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 关闭所有定时任务
     */
    public void shutdownAllJobs() {
        try {
            Scheduler scheduler = stdSchedulerFactory.getScheduler();
            if (!scheduler.isShutdown()) {
                scheduler.shutdown();
            }
        } catch (Exception e) {
            LOG.error("停止任务失败！",e);
            throw new RuntimeException(e);
        }
    }

}
