package com.suitupmonkey.system.controller;

import com.suitupmonkey.enums.WarningTips;
import com.suitupmonkey.system.bean.User;
import com.suitupmonkey.system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@Slf4j
public class UserController {


    @Autowired
    UserService loginService;


    @GetMapping({"/",""})
    String login(){
        return "login";
    }

    @RequestMapping("/userExist")
    @ResponseBody
    String userExist(@RequestBody User user){
        Object object = loginService.test(user);
        if(object instanceof WarningTips){
            log.error("接口参数校验未通过，{}",((WarningTips) object).getMessage());
            return "1";
        }

        return "0";
    }

    @GetMapping("/chatroom")
    public String chatroom(){
        return "chatroom/chatroom";
    }
}
