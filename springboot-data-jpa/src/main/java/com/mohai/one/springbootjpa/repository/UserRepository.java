package com.mohai.one.springbootjpa.repository;

import com.mohai.one.springbootjpa.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Integer> {

    List<UserEntity> findAllByName(String name);

    @Modifying
    @Query(value = "insert into user(id,name,age) values(:id,:name,:age)",nativeQuery = true)
    int insertNameAndAge(@Param("id") Integer id, @Param("name") String name, @Param("age") int age);

}
