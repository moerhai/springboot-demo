package com.mohai.one.springbootbatch.dao;

import com.mohai.one.springbootbatch.domain.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/8/17 07:18
 */
@Repository
public interface UserRepository extends JpaRepository<UserDTO,Integer> {
}
