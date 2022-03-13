package com.mohai.one.scheduled.quartzbatch.config;

import com.mohai.one.scheduled.quartzbatch.bean.QuartzSpringBeanJobFactory;
import com.mohai.one.scheduled.quartzbatch.core.SpringQuartzJobLauncher;
import org.quartz.JobDetail;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * quartz 与 Spring 集成
 *
 * @Auther: moerhai@qq.com
 * @Date: 2020/9/5 09:00
 */
@Configuration
public class SchedulerConfig {

    @Bean
    public JobFactory jobFactory() {
        return new QuartzSpringBeanJobFactory();
    }

    /**
     * 调度工厂类，指定单个或多个触发器
     *
     * @return
     * @throws Exception
     */
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(DataSource dataSource, CronTriggerFactoryBean cronTriggerFactoryBean) throws Exception {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        // 允许覆盖已存在的任务
        factory.setOverwriteExistingJobs(true);
        // 设置数据源
        factory.setDataSource(dataSource);
        //设置JobFactory
        factory.setJobFactory(jobFactory());
        // 设置属性
        factory.setQuartzProperties(quartzProperties());
        factory.setTriggers(cronTriggerFactoryBean.getObject());
        return factory;
    }

    @Bean
    public JobDetailFactoryBean jobDetailFactoryBean() {
        //设置定时调度批量任务，jobName参见BatchConfig中的配置
        JobDetailFactoryBean jobDetailFactoryBean = createJobDetail("sendMessageJob");
        return jobDetailFactoryBean;
    }

    @Bean
    public CronTriggerFactoryBean cronTriggerFactoryBean(JobDetailFactoryBean jobDetailFactoryBean) {
        //定义触发器，每天9点整触发一次  0 0 9 * * ?
        return createTrigger(jobDetailFactoryBean.getObject(), "0 * * * * ?");
    }

    /**
     * 调度触发器
     *
     * @param detail     指定工作类
     * @param expression 指定调度的cron表达式
     * @return
     */
    private CronTriggerFactoryBean createTrigger(JobDetail detail, String expression) {
        CronTriggerFactoryBean cron = new CronTriggerFactoryBean();
        cron.setJobDetail(detail);
        cron.setCronExpression(expression);
        return cron;
    }

    /**
     * 调度工作类 通过jobClass属性指定调度工作类
     *
     * @param jobName 任务名称
     * @return
     */
    private JobDetailFactoryBean createJobDetail(String jobName) {
        JobDetailFactoryBean factory = new JobDetailFactoryBean();
        //设置QuartzJobBean任务的实现
        factory.setJobClass(SpringQuartzJobLauncher.class);
        //任务的参数配置
        Map<String, Object> map = new HashMap<>();
        //spring batch的任务名称 参见BatchConfig中的配置
        map.put("jobName", jobName);
        factory.setJobDataAsMap(map);
        return factory;
    }

    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

}