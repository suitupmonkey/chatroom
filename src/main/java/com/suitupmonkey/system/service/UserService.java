package com.suitupmonkey.system.service;

import com.suitupmonkey.system.bean.User;

public interface UserService {

    int userExist(User user);//if user exist

    User findUser(String username);//find user with it's name
}
