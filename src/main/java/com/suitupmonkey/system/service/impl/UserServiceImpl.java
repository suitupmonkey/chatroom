package com.suitupmonkey.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.suitupmonkey.system.bean.User;
import com.suitupmonkey.system.dao.UserMapper;
import com.suitupmonkey.system.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public void test() {

    }
}
