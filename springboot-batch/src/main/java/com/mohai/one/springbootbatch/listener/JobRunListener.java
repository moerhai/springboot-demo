package com.mohai.one.springbootbatch.listener;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/8/11 00:11
 */
public class JobRunListener implements JobExecutionListener {

    /**
     * 批处理开始前执行
     * @param jobExecution
     */
    @Override
    public void beforeJob(JobExecution jobExecution) {
        System.out.println(String.format("任务id为【%s】 开始执行时间：%s", jobExecution.getJobId(), jobExecution.getStartTime()));
    }

    /**
     * 批处理结束后执行
     * @param jobExecution
     */
    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            System.out.println(String.format("任务id为【%s】 执行结束时间：%s", jobExecution.getJobId(), jobExecution.getEndTime()));
        } else {
            System.out.println(String.format("任务id为【%s】 执行异常状态：%s", jobExecution.getJobId(),  jobExecution.getStatus()));
        }
    }
}
