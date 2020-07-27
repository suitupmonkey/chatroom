package com.suitupmonkey.common.aop;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

/**
 * @author：louweiwei
 * @date：2020/7/27 9:37
 * @description：方法拦截测试
 */

@Slf4j
public class MethodInvocationTest implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Method method = methodInvocation.getMethod();
        Object[] arguments = methodInvocation.getArguments();
        log.info("当前访问方法-> {}, 参数-> {}",method,arguments);
        return null;
    }
}
