package com.suitupmonkey.common.aop;

import com.suitupmonkey.common.utils.ApplicationContextUtil;
import com.suitupmonkey.enums.WarningTips;
import com.suitupmonkey.system.bean.User;
import com.suitupmonkey.system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;

/**
 * @author：louweiwei
 * @date：2020/7/27 9:37
 * @description：方法拦截测试
 */

@Slf4j
public class MethodInvocationTest implements MethodInterceptor {
    @Autowired
    UserService userService;

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Method method = methodInvocation.getMethod();
        Object[] arguments = methodInvocation.getArguments();

        log.info("当前访问方法-> {}, 参数-> {}",method,arguments);
//        AopUtils.invokeJoinpointUsingReflection(methodInvocation.getThis(),method,arguments);
        Object checkPropertiesHandler = ApplicationContextUtil.getBean("checkPropertiesHandler", CheckPropertiesHandler.class);
        CheckPropertiesHandler checkPropertiesHandler1 = (CheckPropertiesHandler)checkPropertiesHandler;
        WarningTips warningTips = checkPropertiesHandler1.verify(User.builder().build());
        return warningTips;
    }
}
