package com.mohai.one.springbootkafka.domain;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/11/10 07:25
 */
@Data
@Accessors(chain=true)
public class UserLog {

    private String id;
    private String username;
    private String state;

}