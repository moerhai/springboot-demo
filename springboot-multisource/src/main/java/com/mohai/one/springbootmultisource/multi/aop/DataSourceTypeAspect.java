package com.mohai.one.springbootmultisource.multi.aop;

import com.mohai.one.springbootmultisource.multi.DataSourceTypeEnum;
import com.mohai.one.springbootmultisource.multi.DataSourceTypeManager;
import com.mohai.one.springbootmultisource.multi.annotation.DataSourceType;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.lang.reflect.Method;

@Aspect
@Component
public class DataSourceTypeAspect {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceTypeAspect.class);

    @Before("@annotation(com.mohai.one.springbootmultisource.multi.annotation.DataSourceType)")
    public void doBefore(JoinPoint point) {
        Method method = ((MethodSignature) point.getSignature()).getMethod();
        //获取方法上的注解
        DataSourceType dataSourceType = method.getAnnotation(DataSourceType.class);
        //获取注解上的配置信息
        DataSourceTypeEnum type = dataSourceType.value();
        if(type != null){
            //设置当前的数据库连接池
            DataSourceTypeManager.setDataSource(type);
        }else{
            //如果没有设置使用默认的
            DataSourceTypeManager.setDataSource(DataSourceTypeEnum.MASTER);
        }
    }

    @After("@annotation(com.mohai.one.springbootmultisource.multi.annotation.DataSourceType)")
    public void doAfter(JoinPoint point) {
        //清理当前设置的数据库连接池，恢复默认的数据库连接池
        DataSourceTypeManager.clearDataSource();
    }

}
