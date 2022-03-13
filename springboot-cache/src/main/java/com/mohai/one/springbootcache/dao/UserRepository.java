package com.mohai.one.springbootcache.dao;

import com.mohai.one.springbootcache.domain.UserDTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/8/2 00:27
 */
@Repository
public interface UserRepository extends CrudRepository<UserDTO,Long> {
    UserDTO findByName(String name);
}
