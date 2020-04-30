package com.suitupmonkey.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.suitupmonkey.system.bean.User;
import org.springframework.stereotype.Component;

@Component
public interface UserMapper extends BaseMapper<User> {
    //if user exist
    int userExist(User user);
    //find user with it's name
    User findUser(String username);
}
