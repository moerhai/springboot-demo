package com.mohai.one.springbootbatch.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/8/11 00:07
 */
@RestController
@RequestMapping("/user")
public class UserJobController {

    @Autowired
    JobLauncher jobLauncher;

    /**
     * 除了将字段importJob名称改成importUserJob外，
     * 还可以提供了一个@Qualifier注解，来指定需要装配bean的名称
     */
    @Autowired
    Job importUserJob;

    @RequestMapping("/start")
    public String runJob() throws Exception{
        // 通过使用JobParameters绑定设置参数
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .addDate("date",new Date())
                .toJobParameters();
        jobLauncher.run(importUserJob, jobParameters);
        return "SUCCESS";
    }

}
