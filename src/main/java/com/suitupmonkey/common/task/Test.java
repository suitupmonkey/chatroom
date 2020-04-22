package com.suitupmonkey.common.task;

import com.suitupmonkey.system.bean.User;
import com.suitupmonkey.system.cache.entityService.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @program: chatroom
 * @description: 测试redis
 * @author: louweiwei
 * @create: 2020-04-22 10:24
 **/
@Component
@EnableScheduling
public class Test {

    @Autowired
    UserRepository userRepository;

    @Scheduled(cron = "0 56 10 * * *")
    public void test(){
        User user = new User();
        user.setUsername("lancelot");
        user.setHead("queen's lover");
        User save = userRepository.save(user);
        System.out.println("被保存的用户-> " + save);
        String userId = user.getId();
        Optional<User> byId = userRepository.findById(userId);
        System.out.println("查询之前保存的用户-> " + byId);
        long count = userRepository.count();
        System.out.println("保存了用户的个数-> " + count);
        userRepository.delete(user);
        Optional<User> deletedUser = userRepository.findById(userId);
        System.out.println("之前被删除的用户-> " + deletedUser);
    }
}
