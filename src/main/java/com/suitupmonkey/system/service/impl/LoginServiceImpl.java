package com.suitupmonkey.system.service.impl;

import com.suitupmonkey.system.bean.User;
import com.suitupmonkey.system.dao.LoginMapper;
import com.suitupmonkey.system.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    LoginMapper loginMapper;


    @Override
    public int userExist(User user) {
        return loginMapper.userExist(user);
    }

    @Override
    public User findUser(String username) {
        return loginMapper.findUser(username);
    }
}
