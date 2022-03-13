package com.mohai.one.springbootlombok.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Date 2020/5/29 23:03
 * @Created by moerhai@qq.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {

    private String id;
    private String name;
    private int age;

}
