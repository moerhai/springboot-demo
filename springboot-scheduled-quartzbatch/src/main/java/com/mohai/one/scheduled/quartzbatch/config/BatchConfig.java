package com.mohai.one.scheduled.quartzbatch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.mail.SimpleMailMessageItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.sql.DataSource;
import java.util.Date;
import java.util.Map;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/9/6 14:12
 */
@Configuration
@EnableBatchProcessing
public class BatchConfig {

    /**
     * 用于构建JOB
     */
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    /**
     * 用于构建Step
     */
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private DataSource dataSource;

    @Value("${spring.mail.username}")
    private String from;

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.password}")
    private String password;

    /**
     * 注入job
     *
     * @return
     */
    @Bean
    public Job sendMessageJob() {
        return jobBuilderFactory.get("sendMessageJob")
                .incrementer(new RunIdIncrementer())
                .start(sendStep())
                .build();
    }

    /**
     * 注入step
     *
     * @return
     */
    @Bean
    @JobScope
    public Step sendStep() {
        return stepBuilderFactory.get("sendStep")
                //每次执行的次数
                .chunk(100)
                //设置读处理器
                .reader(reader())
                //设置处理器
                .processor(processor())
                //设置写处理器
                .writer(writer())
                .build();
    }

    public JdbcCursorItemReader<Map<String, Object>> reader() {
        JdbcCursorItemReader<Map<String, Object>> reader = new JdbcCursorItemReader<>();
        reader.setDataSource(dataSource);
        //查询message表中数据
        reader.setSql("select * from message");
        reader.setRowMapper(new ColumnMapRowMapper());
        return reader;
    }

    public ItemProcessor processor() {
        // 处理数据封装成SimpleMailMessage
        ItemProcessor itemProcessor = new ItemProcessor() {
            @Override
            public Object process(Object item) throws Exception {
                Map<String, Object> map = (Map<String, Object>) item;
                SimpleMailMessage message = new SimpleMailMessage();
                //设置邮件发送者
                message.setFrom(from);
                //设置邮件接收用户
                message.setTo(map.get("user").toString());
                //设置邮件主题
                message.setSubject(map.get("title").toString());
                //设置邮件内容
                message.setText(map.get("content").toString());
                message.setSentDate(new Date());
                return message;
            }
        };
        return itemProcessor;
    }

    public SimpleMailMessageItemWriter writer() {
        SimpleMailMessageItemWriter simpleMailMessageItemWriter = new SimpleMailMessageItemWriter();
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        //设置邮件服务器地址
        javaMailSender.setHost(host);
        //设置邮件服务密码
        javaMailSender.setPassword(password);
        //设置用户名称
        javaMailSender.setUsername(from);
        simpleMailMessageItemWriter.setMailSender(javaMailSender);
        return simpleMailMessageItemWriter;
    }

    @Bean
    public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor(JobRegistry jobRegistry){
        JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor = new JobRegistryBeanPostProcessor();
        jobRegistryBeanPostProcessor.setJobRegistry(jobRegistry);
        return jobRegistryBeanPostProcessor;
    }

}