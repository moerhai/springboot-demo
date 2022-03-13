package com.mohai.one.springbootcacheehcache.dao;


import com.mohai.one.springbootcacheehcache.domain.UserDTO;
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
