package com.mohai.one.app.wares.service;

import com.mohai.one.app.wares.domain.AdminUser;
import com.mohai.one.app.wares.dao.AdminUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/11/21 00:41
 */
@Service
public class AdminUserService {

    @Autowired
    private AdminUserMapper adminUserMapper;

    public AdminUser getAdminUserByName(String name){
        return adminUserMapper.getAdminUserByName(name);
    }

}
