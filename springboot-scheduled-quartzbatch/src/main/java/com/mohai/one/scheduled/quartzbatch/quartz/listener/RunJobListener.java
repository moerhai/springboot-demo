package com.mohai.one.scheduled.quartzbatch.quartz.listener;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.listeners.JobListenerSupport;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/9/5 23:12
 */
public class RunJobListener extends JobListenerSupport {

    @Override
    public String getName() {
        String name = getClass().getSimpleName();
        return name;
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        String name = context.getJobDetail().getKey().getName();
        System.out.println(name + " jobToBeExecuted ");
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        String name = context.getJobDetail().getKey().getName();
        System.out.println(name + " jobWasExecuted ");
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        String name = context.getJobDetail().getKey().getName();
        System.out.println(name + " jobExecutionVetoed ");
    }

}
