package com.suitupmonkey.system.config;

import com.suitupmonkey.system.bean.User;
import org.apache.shiro.SecurityUtils;

import java.io.Serializable;


//定义一些全局变量，目前只保存当前用户
public class GlobalVarisbles implements Serializable {

    //获取当前用户
    public static User currentUser(){
        User subject = (User) SecurityUtils.getSubject().getPrincipal();
        String username = subject.getUsername();
        return subject;
    }

    //获取当前用户ID
    public static String userId(){
        return currentUser().getId();
    }

    //获取当前用户名
    public static String username(){
        return currentUser().getUsername();
    }

}
