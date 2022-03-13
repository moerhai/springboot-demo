package com.mohai.one.scheduled.quartzbatch.batch.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/9/6 14:48
 */
public class BatchJobExecutionListener extends JobExecutionListenerSupport {

    @Override
    public void beforeJob(JobExecution jobExecution) {
        super.beforeJob(jobExecution);
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        super.afterJob(jobExecution);
    }
}
