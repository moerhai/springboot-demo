package com.mohai.one.springbootmybatis.interceptor;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * 拦截Executor，并打印所有执行的SQL，以及执行时间
 */
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class})
})
public class CommonInterceptor implements Interceptor {

    private static Logger logger = LoggerFactory.getLogger(CommonInterceptor.class);

    //暂时未使用
    private Properties properties = new Properties();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        long start = System.currentTimeMillis();
        try{
            return invocation.proceed();
        }finally {
            long end = System.currentTimeMillis();
            Object[] args = invocation.getArgs();
            //获取MappedStatement，对应XML文件中的一个SQL语句
            MappedStatement mappedStatement = (MappedStatement) args[0];
            //获取参数
            Object parameter = args[1];
            BoundSql boundSql = mappedStatement.getBoundSql(parameter);
            String sql = boundSql.getSql();
            //打印执行的SQL和参数
            logger.info("==> execute SQL [{}] , Parameters is [{}]", sql,parameter);
            logger.info("==> execute SQL cost [{}] ms", (end - start));//打印SQL执行时间

        }
    }
    //当前拦截器生成一个代理放到拦截器链中
    @Override
    public Object plugin(Object target) {
        //默认实现逻辑
        return Plugin.wrap(target, this);
    }
    //拦截器初始化的时候调用，也只调用一次，定义插件配置的属性
    @Override
    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}