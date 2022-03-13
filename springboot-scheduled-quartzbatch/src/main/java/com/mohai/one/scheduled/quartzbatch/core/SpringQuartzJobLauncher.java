package com.mohai.one.scheduled.quartzbatch.core;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.configuration.ListableJobLocator;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * QuartzJobBean类起连接quartz和spring batch的作用
 * 由于该类是直接在quartz中实例化出来的，不受spring的管理
 * 所以利用SpringBeanJobFactory将其注入Spring中
 *
 * @Auther: moerhai@qq.com
 * @Date: 2020/9/6 14:53
 */
public class SpringQuartzJobLauncher extends QuartzJobBean {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private ListableJobLocator jobLocator;

    @Autowired
    private Job job;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        JobDataMap map = context.getMergedJobDataMap();
        String jobName = map.getString("jobName");
        System.out.println("当前任务[" + jobName + "]正常被调起");

        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
        try {
            System.out.println(jobLocator.getJob(jobName));
            //  Job job = jobLocator.getJob(jobName);
            jobLauncher.run(job, jobParameters);
        } catch (JobExecutionAlreadyRunningException e) {
            e.printStackTrace();
        } catch (JobRestartException e) {
            e.printStackTrace();
        } catch (JobInstanceAlreadyCompleteException e) {
            e.printStackTrace();
        } catch (JobParametersInvalidException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}