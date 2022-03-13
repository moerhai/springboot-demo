package com.mohai.one.app.core.user.dao;

import com.mohai.one.app.core.user.domain.AdminUserInfo;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/11/29 15:42
 */
public interface AdminUserInfoMapper {

    AdminUserInfo findByName(String username);

}