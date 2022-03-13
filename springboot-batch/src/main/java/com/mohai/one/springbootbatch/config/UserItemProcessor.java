package com.mohai.one.springbootbatch.config;

import com.mohai.one.springbootbatch.domain.UserDTO;
import org.springframework.batch.item.validator.ValidatingItemProcessor;

import java.util.Date;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/8/13 00:48
 */
public class UserItemProcessor extends ValidatingItemProcessor<UserDTO> {
    @Override
    public UserDTO process(UserDTO item) {
        //需执行super.process(item)才会调用自定义校验器。
        super.process(item);
        // 设置默认值
        item.setStatus((byte) 1);
        item.setCreateTime(new Date());
        return item;
    }
}