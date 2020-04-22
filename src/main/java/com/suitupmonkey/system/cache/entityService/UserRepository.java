package com.suitupmonkey.system.cache.entityService;

import com.suitupmonkey.system.bean.User;
import org.springframework.data.repository.CrudRepository;

/**
 * @program: chatroom
 * @description: Basic Repository Interface To Persist Person Entities
 * @author: louweiwei
 * @create: 2020-04-22 10:15
 **/
public interface UserRepository extends CrudRepository<User, String> {
}
