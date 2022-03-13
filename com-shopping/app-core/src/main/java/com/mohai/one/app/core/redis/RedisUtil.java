package com.mohai.one.app.core.redis;

import io.lettuce.core.SetArgs;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.cluster.api.async.RedisAdvancedClusterAsyncCommands;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/11/26 01:28
 */
@Component
public class RedisUtil {

    private static final Logger LOG = LoggerFactory.getLogger(RedisUtil.class);
//    /**
//     * 解锁的lua脚本
//     */
//    private static final String UNLOCK_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
//    /**
//     * 尝试间隔时间(ms 毫秒)
//     */
//    private static final int RETRY_TIME = 100;
//    /**
//     * 锁标记
//     */
//    private volatile boolean locked = false;
//
//    /**
//     * 默认请求锁的超时时间(ms 毫秒)
//     */
//    private static final long TIME_OUT = 100;
//    /**  默认过期时长，单位：秒 */
//    public final static long DEFAULT_EXPIRE = 60 * 60 * 24;
//    /**  不设置过期时长 */
//    public final static long NOT_EXPIRE = -1;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 字符串缓存获取
     * @param key 键
     * @return 值
     */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 删除缓存
     * @param key 可以传一个值 或多个
     */
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    /**
     *
     * @param key
     * @param value
     * @param time
     * @return
     */
    public boolean set(String key, Object value, long time) {
        return set(key, value, time, TimeUnit.SECONDS);
    }

    /**
     * 放入缓存并设置时间
     * @param key   键
     * @param value 值
     * @param time  时间
     * @param timeUnit 类型
     * @return true成功 false 失败
     */
    public boolean set(String key, Object value, long time, TimeUnit timeUnit) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, timeUnit);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 普通缓存放入
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

//    /**
//     * 尝试获取锁 超时返回
//     * @param lockKey
//     * @param lockValue
//     * @param waitTime    等待时间,单位为毫秒
//     * @param expireTime  强制锁释放时间,单位为毫秒
//     * @return
//     */
//    public boolean tryLock(String lockKey, String lockValue, int waitTime, int expireTime) {
//        int alreadyWaitTime = 0;
//        while (waitTime > alreadyWaitTime) {
//            if (lock(lockKey, lockValue, expireTime)) {
//                locked = true;
//                return true;
//            } else {
//                try {
//                    Thread.sleep(RETRY_TIME);
//                } catch (InterruptedException e) {
//                    LOG.error("Get distributed lock sleep interrupted:{}",e);
//                    return false;
//                }
//            }
//            alreadyWaitTime += RETRY_TIME;
//        }
//        return false;
//    }
//
//    /**
//     * 尝试获取锁 立即返回
//     * @param lockKey 锁
//     * @param lockValue 请求标识
//     * @param expireTime 超期时间
//     * @return 是否获取成功
//     */
//    public boolean lock(String lockKey, String lockValue, int expireTime) {
//        locked = setLock(lockKey, lockValue, expireTime);
//        if (locked) {
//            return true;
//        }
//        return false;
//    }
//
//    /**
//     * 以阻塞方式的获取锁
//     * @param lockKey
//     * @param lockValue
//     * @param expireTime
//     * @return
//     */
//    public boolean lockBlock(String lockKey, String lockValue, int expireTime) {
//        while (true) {
//            locked = setLock(lockKey, lockValue, expireTime);
//            if (locked) {
//                return true;
//            }
//            try {
//                Thread.sleep(10,50000);
//            } catch (InterruptedException e) {
//                LOG.error("Get distributed lock sleep interrupted:{}",e);
//            }
//        }
//    }
//
//    /**
//     * 获取分布式锁，原子操作
//     * @param lockKey
//     * @param lockValue  唯一值,可以使用UUID.randomUUID().toString();
//     * @param expire
//     * @param timeUnit
//     * @return
//     */
//    public Boolean tryLock(String lockKey, String lockValue, long expire, TimeUnit timeUnit) {
//        try{
//            RedisCallback<Boolean> callback = (connection) -> {
//                return connection.set(lockKey.getBytes(Charset.forName("UTF-8")), lockValue.getBytes(Charset.forName("UTF-8")), Expiration.seconds(timeUnit.toSeconds(expire)), RedisStringCommands.SetOption.SET_IF_ABSENT);
//            };
//            locked = (Boolean)redisTemplate.execute(callback);
//            return locked;
//        } catch (Exception e) {
//            LOG.error("redis lock error:{}", e);
//        }
//        return false;
//    }
//
//    /**
//     * 释放分布式锁
//     * 不建议使用固定的字符串作为键的值，而是设置一个不可猜测(non-guessable)的长随机字符串。
//     * 不建议使用 DEL 命令来释放锁，而是发送一个 Lua 脚本，这个脚本只在客户端传入的值和键相匹配时，才对键进行删除。
//     * @param lockKey 锁
//     * @param lockValue 请求标识
//     * @return 是否释放成功
//     */
//    public Boolean unlock(String lockKey, String lockValue) {
//        if(locked){
//            RedisCallback<Boolean> callback = (connection) -> {
//                return connection.eval(UNLOCK_SCRIPT.getBytes(), ReturnType.BOOLEAN ,1, lockKey.getBytes(Charset.forName("UTF-8")), lockValue.getBytes(Charset.forName("UTF-8")));
//            };
//            locked = !(Boolean)redisTemplate.execute(callback);
//            return (Boolean)redisTemplate.execute(callback);
//        }
//        return true;
//    }
//
//    private Boolean setLock(final String lockKey, final String lockValue, final long seconds) {
//        Assert.isTrue(!StringUtils.isBlank(lockKey), "key不能为空");
//        return (Boolean) redisTemplate.execute(new RedisCallback<Boolean>() {
//            @Override
//            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
//                Object nativeConnection = connection.getNativeConnection();
//                LOG.info("获取本地连接类型:{}",nativeConnection);
//                boolean result = connection.set(lockKey.getBytes(Charset.forName("UTF-8")), lockValue.getBytes(Charset.forName("UTF-8")), Expiration.seconds(TimeUnit.SECONDS.toSeconds(seconds)), RedisStringCommands.SetOption.SET_IF_ABSENT);;
//                if (result) {
//                    LOG.info("获取锁的时间:{}",System.currentTimeMillis());
//                }
//                return result;
//            }
//        });
//    }

}