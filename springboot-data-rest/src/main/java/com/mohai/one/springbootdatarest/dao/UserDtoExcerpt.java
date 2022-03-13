package com.mohai.one.springbootdatarest.dao;

import com.mohai.one.springbootdatarest.domain.UserDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/7/22 01:24
 */

/**
 * 自定义返回字段属性
 */
@Projection(name = "virtual", types = UserDTO.class)
public interface UserDtoExcerpt {

    String getFirstName();

    String getLastName();

    String getSex();

    @Value("#{target.firstName} #{target.lastName}")
    String getFullName();

    @Value("#{target.addressDTO.toString()}")
    String getAddressDTO();

}
