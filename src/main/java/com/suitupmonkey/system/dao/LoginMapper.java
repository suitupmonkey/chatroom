package com.suitupmonkey.system.dao;

import com.suitupmonkey.system.bean.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginMapper {


    int userExist(User user);//if user exist

    User findUser(String username);//find user with it's name
}
