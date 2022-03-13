package com.mohai.one.scheduled.quartzbatch.bean;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

/**
 * 自定义JobFactory，手动将实例注入到Spring容器中
 * @Auther: moerhai@qq.com
 * @Date: 2020/9/5 09:05
 */
public class QuartzSpringBeanJobFactory extends SpringBeanJobFactory implements ApplicationContextAware {

    private transient AutowireCapableBeanFactory beanFactory;

    @Override
    public void setApplicationContext(final ApplicationContext context) {
        beanFactory = context.getAutowireCapableBeanFactory();
    }

    @Override
    protected Object createJobInstance(final TriggerFiredBundle bundle) throws Exception {
        //创建jobInstance
        final Object job = super.createJobInstance(bundle);
        //向Spring中注入这个bean
        beanFactory.autowireBean(job);
        return job;
    }

}
