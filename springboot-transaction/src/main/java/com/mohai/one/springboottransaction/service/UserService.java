package com.mohai.one.springboottransaction.service;

import com.mohai.one.springboottransaction.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;



}
