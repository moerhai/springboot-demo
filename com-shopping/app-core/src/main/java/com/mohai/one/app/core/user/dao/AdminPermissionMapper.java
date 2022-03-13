package com.mohai.one.app.core.user.dao;

import com.mohai.one.app.core.user.domain.AdminPermission;

import java.util.List;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/12/19 00:59
 */
public interface AdminPermissionMapper {

    List<AdminPermission> selectPermListByUserId(long userId);

    List<AdminPermission> selectPermListByPath(String requestUrl);

}