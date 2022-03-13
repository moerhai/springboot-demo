package com.mohai.one.app.wares.dao;

import com.mohai.one.app.wares.domain.AdminUser;

public interface AdminUserMapper {
    int deleteByPrimaryKey(Long userId);

    int insert(AdminUser record);

    int insertSelective(AdminUser record);

    AdminUser selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(AdminUser record);

    int updateByPrimaryKey(AdminUser record);

    AdminUser getAdminUserByName(String name);
}