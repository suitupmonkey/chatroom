package com.suitupmonkey.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.suitupmonkey.system.bean.User;
import org.springframework.stereotype.Component;

@Component
public interface UserMapper extends BaseMapper<User> {
}
