package com.suitupmonkey.system.controller;

import com.suitupmonkey.system.bean.User;
import com.suitupmonkey.system.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
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
        try{
            int i = loginService.userExist(user);
            if(i > 0) return "0";
        }catch (AuthenticationException e){
            e.printStackTrace();
            return "1";
        }
        return "0";
    }

    @GetMapping("/chatroom")
    public String chatroom(){
        return "chatroom/chatroom";
    }
}
