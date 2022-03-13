package com.mohai.one.springbootjdbctemplate.service;

import com.mohai.one.springbootjdbctemplate.domain.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //查
    public List<UserEntity> getAll(){
       return jdbcTemplate.query("select * from user", new BeanPropertyRowMapper(UserEntity.class));
    }

    //增
    @Transactional
    public int insertUser(UserEntity userEntity){
        String sql = "insert into user(name,age) values(?,?)";
        return jdbcTemplate.update(sql, userEntity.getName(), userEntity.getAge());
    }

    //改
    @Transactional
    public int updateUserById(UserEntity userEntity){
        String sql = "update user set name=?,age=? where id=?";
        return jdbcTemplate.update(sql, userEntity.getName(), userEntity.getAge(),userEntity.getId());
    }

    //删
    @Transactional
    public int deleteUserById(int id){
        String sql = "delete from user where id = ?";
        return jdbcTemplate.update(sql, id);
    }

}