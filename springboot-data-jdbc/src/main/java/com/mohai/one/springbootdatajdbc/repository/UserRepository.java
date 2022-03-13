package com.mohai.one.springbootdatajdbc.repository;

import com.mohai.one.springbootdatajdbc.domain.UserEntity;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends CrudRepository<UserEntity,Integer> {

    List<UserEntity> findAll();

    @Modifying
    @Query("insert into user(id,name,age) values(:id,:name,:age)")
    int insertNameAndAge(@Param("id") Integer id, @Param("name") String name, @Param("age") int age);

    @Modifying
    @Query("update user set name = :name,age = :age where id = :id")
    int updateNameAndAge(@Param("id") Integer id, @Param("name") String name, @Param("age") int age);

    @Query("select * from user u where u.name = :name")
    List<UserEntity> findByName(@Param("name") String name);

}