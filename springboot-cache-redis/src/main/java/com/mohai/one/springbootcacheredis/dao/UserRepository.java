package com.mohai.one.springbootcacheredis.dao;

import com.mohai.one.springbootcacheredis.domain.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/8/2 00:27
 */
@Repository
public interface UserRepository extends JpaRepository<UserDTO,Long> {
    UserDTO findByName(String name);
}
