package com.suitupmonkey.system.service.impl;

import com.suitupmonkey.system.bean.User;
import com.suitupmonkey.system.dao.UserMapper;
import com.suitupmonkey.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;


    @Override
    public int userExist(User user) {
        return userMapper.userExist(user);
    }

    @Override
    public User findUser(String username) {
        return userMapper.findUser(username);
    }
}
