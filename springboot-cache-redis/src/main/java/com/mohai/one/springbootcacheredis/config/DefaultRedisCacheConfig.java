package com.mohai.one.springbootcacheredis.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKey;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Arrays;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/8/5 07:23
 */
@Configuration
@ConfigurationProperties(prefix = "spring.cache.redis")
public class DefaultRedisCacheConfig extends CachingConfigurerSupport {

    //从properties文件中获取
    private Duration timeToLive = Duration.ZERO;
    public void setTimeToLive(Duration timeToLive) {
        this.timeToLive = timeToLive;
    }

    private final RedisTemplate redisTemplate;

    @Autowired
    public DefaultRedisCacheConfig(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);

        // 使用Jackson2JsonRedisSerialize 替换默认序列化
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);

        ObjectMapper objectMapper = new ObjectMapper();
        // 指定要序列化的域和可见性，ALL表示可以访问所有的属性，ANY表示所有的包括private和public可见
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        // 设置value的序列化规则和 key的序列化规则
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }

    @Override
    public CacheManager cacheManager() {
        // 使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);

        // 自定义ObjectMapper
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(mapper);

        // 配置序列化
        RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(timeToLive) //定义默认的失效时间
                .disableCachingNullValues()//不允许存null值，如果返回null则报错
                .computePrefixWith(cacheName -> "APPName".concat(":").concat(cacheName).concat(":"))//定义key值前缀
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer));

       RedisCacheManager cacheManager = RedisCacheManager.builder(redisTemplate.getConnectionFactory())
               .cacheDefaults(cacheConfiguration)
                .build();

        return cacheManager;
    }

    /**
     * key值生成策略
     * 如果缓存注解@Cacheable、@CacheEvict等中指定key属性，那么会覆盖此key生成器
     * @return
     */
    @Override
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                return new DefaultKey(target,method,params);
            }
        };
    }

    class DefaultKey implements Serializable{

        /** 调用目标对象全类名 */
        protected String targetClassName;
        /** 调用目标方法名称 */
        protected String methodName;
        /** 调用目标参数 */
        protected Object[] params;

        public DefaultKey(Object target, Method method, Object... elements) {
            this.targetClassName = target.getClass().getName();
            this.params = elements.clone();
            StringBuilder builder = new StringBuilder();
            builder.append(target.getClass().getName()).append("#");
            builder.append(method.getName()).append("(");
            int n = 0;
            for (Object obj : elements) {
                if(n > 0){
                    builder.append(",").append(obj.toString());
                }else{
                    builder.append(obj.toString());
                }
                n++;
            }
            builder.append(")");
            this.methodName = builder.toString();
        }

        @Override
        public String toString() {
            return "DefaultKey{" +
                    "targetClassName='" + targetClassName + '\'' +
                    ", methodName='" + methodName + '\'' +
                    ", params=" + Arrays.toString(params) +
                    '}';
        }
    }

}