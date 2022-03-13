package com.mohai.one.springbootmultisource.dao;

import com.mohai.one.springbootmultisource.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<UserEntity,Long> {
}
