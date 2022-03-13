package com.mohai.one.scheduled.quartzbatch.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/9/5 09:12
 */
public class ScheduleJob implements Job {

    private static final Logger LOG = LoggerFactory.getLogger(ScheduleJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        
    }
}
