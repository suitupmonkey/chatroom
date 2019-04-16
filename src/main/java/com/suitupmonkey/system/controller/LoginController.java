package com.suitupmonkey.system.controller;

import com.suitupmonkey.system.bean.User;
import com.suitupmonkey.system.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class LoginController {


    @Autowired
    LoginService loginService;


    @GetMapping({"/",""})
    String login(){
        return "login";

    }

    @RequestMapping("/userExist")
    @ResponseBody
    String userExist(@RequestBody User user){
        int userExist = loginService.userExist(user);
        if(userExist > 0){
            return "0";
        }
        return "1";
    }

    @GetMapping("/chatroom")
    public String chatroom(){
        return "chatroom/chatroom";
    }
}
