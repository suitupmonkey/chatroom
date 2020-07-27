package com.suitupmonkey.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.suitupmonkey.system.bean.User;

public interface UserService extends IService<User> {

    Object test(User user);
}
