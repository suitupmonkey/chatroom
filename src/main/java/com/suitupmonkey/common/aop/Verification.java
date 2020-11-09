package com.suitupmonkey.common.aop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.support.RegexpMethodPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: chatroom
 * @description: 入参校验
 * @author: louweiwei
 * @create: 2020-04-29 14:04
 **/
@Slf4j
@Configuration
public class Verification {

    @Bean
    public RegexpMethodPointcutAdvisor regexpMethodPointcutAdvisor(){
        RegexpMethodPointcutAdvisor regexpMethodPointcutAdvisor = new RegexpMethodPointcutAdvisor();
        String pattern = "com.suitupmonkey.system.service.impl.UserServiceImpl.test";
        VerificationInterceptor advice = new VerificationInterceptor();
        regexpMethodPointcutAdvisor.setPattern(pattern);
        regexpMethodPointcutAdvisor.setAdvice(advice);
        return regexpMethodPointcutAdvisor;
    }



}
