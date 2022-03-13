package com.mohai.one.app.core.redis.lock;

import cn.hutool.core.util.StrUtil;
import com.mohai.one.app.core.redis.lock.annotation.RedisLock;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/12/17 00:30
 */
@Aspect
@Component
public class RedisLockAspect {

    @Around("@annotation(redisLock)")
    public Object around(ProceedingJoinPoint joinPoint, RedisLock redisLock) throws Throwable {
        String lockName = redisLock.lockName();
        String lockValueExpression = redisLock.lockValueExpression();

//        RLock rLock = redissonClient.getLock(getRedisKey(joinPoint,lockName,spel));
//
//        rLock.lock(redisLock.expire(),redisLock.timeUnit());

        Object result = null;
        try {
            //执行方法
            result = joinPoint.proceed();

        } finally {
          //  rLock.unlock();
        }
        return result;
    }


    private String getRedisKey(ProceedingJoinPoint joinPoint,String lockName,String lockValueExpression) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method targetMethod = methodSignature.getMethod();
        Object target = joinPoint.getTarget();
        Object[] arguments = joinPoint.getArgs();
        return "";
    }

}
