package com.mohai.one.app.wares.controller;

import com.mohai.one.app.core.dto.ResultDto;
import com.mohai.one.app.core.utils.BeanUtil;
import com.mohai.one.app.wares.domain.AdminUser;
import com.mohai.one.app.wares.dto.AdminUserDTO;
import com.mohai.one.app.wares.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/11/21 00:44
 */
@RestController
@RequestMapping("/wares/adminUser")
public class AdminUserController {

    @Autowired
    private AdminUserService adminUserService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping("/get")
    public ResultDto<AdminUserDTO> get(@RequestParam("name") String name){
         ResultDto<AdminUserDTO> resultDto = new ResultDto<>();
         AdminUser adminUser = adminUserService.getAdminUserByName(name);
         resultDto.setData(BeanUtil.copyProperties(adminUser,AdminUserDTO.class));
         return resultDto;
    }

}