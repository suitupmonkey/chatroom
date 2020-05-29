package com.suitupmonkey.common.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: chatroom
 * @description: 入参校验
 * @author: louweiwei
 * @create: 2020-04-29 14:04
 **/
@Slf4j
@Aspect
@Component
public class Verification {

    @Pointcut("execution(* com.suitupmonkey.system.service.impl.UserServiceImpl.*(..))")
    public void verify(){}

    @Before("verify()")
    @ResponseBody
    public void doBefore(JoinPoint joinPoint){

    }




}
