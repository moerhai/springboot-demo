package com.mohai.one.app.core.redis.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.util.IOUtils;
import com.google.common.base.Preconditions;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStoreSerializationStrategy;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/12/27 02:12
 */
public class FastJsonRedisTokenStoreSerializationStrategy implements RedisTokenStoreSerializationStrategy {

    private final static ParserConfig defaultRedisConfig = new ParserConfig();

    static {
        //设置FastJson Json自动转换为Java对象
       // defaultRedisConfig.setAutoTypeSupport(true);
        defaultRedisConfig.addAccept("com.mohai.one.app.");
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        //校验参数
        Preconditions.checkArgument(clazz != null,
                "clazz can't be null");
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        try {
            return JSON.parseObject(new String(bytes, IOUtils.UTF8), clazz, defaultRedisConfig);
        } catch (Exception ex) {
            throw new SerializationException("Could not serialize: " + ex.getMessage(), ex);
        }
    }

    @Override
    public String deserializeString(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        return new String(bytes, IOUtils.UTF8);
    }

    @Override
    public byte[] serialize(Object object) {
        if (object == null) {
            return new byte[0];
        }
        try {
            return JSON.toJSONBytes(object, SerializerFeature.WriteClassName,
                    SerializerFeature.DisableCircularReferenceDetect);
        } catch (Exception ex) {
            throw new SerializationException("Could not serialize: " + ex.getMessage(), ex);
        }
    }

    @Override
    public byte[] serialize(String data) {
        if (data == null || data.length() == 0) {
            return new byte[0];
        }
        return data.getBytes(IOUtils.UTF8);
    }
}
