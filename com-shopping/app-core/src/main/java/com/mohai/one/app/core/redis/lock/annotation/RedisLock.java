package com.mohai.one.app.core.redis.lock.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * redis分布式锁
 * @Auther: moerhai@qq.com
 * @Date: 2020/12/17 00:26
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RedisLock {

    /**
     * redis锁 名字
     */
    String lockName() default "";

    /**
     * 通过spel表达式获取值
     * @return
     */
    String lockValueExpression() default "";

    /**
     * 过期秒数,默认为5秒
     *
     * @return 轮询锁的时间
     */
    int expire() default 5;

    /**
     * 超时时间单位
     *
     * @return 秒
     */
    TimeUnit timeUnit() default TimeUnit.MILLISECONDS;

}
