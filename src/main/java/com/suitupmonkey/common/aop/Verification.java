package com.suitupmonkey.common.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
@Configuration
public class Verification {

    @Pointcut("execution(* com.suitupmonkey.system.service.impl.UserServiceImpl.*(..))")
    public void verify(){}

    @Before("verify()")
    @ResponseBody
    public void doBefore(JoinPoint joinPoint){

    }

    @Bean
    public DefaultPointcutAdvisor defaultPointcutAdvisor2() {
        String expression = "execution(* com.suitupmonkey.system.service.impl.UserServiceImpl.*(..))";
        MethodInvocationTest interceptor = new MethodInvocationTest();
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(expression);

        // 配置增强类advisor
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
        advisor.setPointcut(pointcut);
        advisor.setAdvice(interceptor);
        return advisor;
    }



}
