package com.suitupmonkey.system.controller;

import com.suitupmonkey.enums.WarningTips;
import com.suitupmonkey.system.bean.User;
import com.suitupmonkey.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
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
        Object test = loginService.test(user);
        if(test instanceof WarningTips){
            return ((WarningTips) test).getMessage();
        }
        return "0";
    }

    @GetMapping("/chatroom")
    public String chatroom(){
        return "chatroom/chatroom";
    }
}
