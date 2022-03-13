package com.mohai.one.springboottransaction.dao;

import com.mohai.one.springboottransaction.entity.AccountEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AccountDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Object getAccount(){
        return jdbcTemplate.queryForObject("", AccountEntity.class);
    }


    public void updateAccount(){


    }

}
