package com.suitupmonkey.common.aop;

import com.suitupmonkey.common.utils.ApplicationContextUtil;
import com.suitupmonkey.enums.WarningTips;
import com.suitupmonkey.system.bean.User;
import com.suitupmonkey.system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author：louweiwei
 * @date：2020/7/27 9:37
 * @description：方法拦截测试
 */

@Slf4j
@Component
public class VerificationInterceptor implements MethodInterceptor {
    @Autowired
    UserService userService;

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Method method = methodInvocation.getMethod();
        Object[] arguments = methodInvocation.getArguments();

        log.info("当前访问方法-> {}, 参数-> {}",method,arguments);
        Object checkPropertiesHandler = ApplicationContextUtil.getBean("propertyCheckingHandler", PropertyCheckingHandler.class);
        PropertyCheckingHandler checker = (PropertyCheckingHandler)checkPropertiesHandler;
        WarningTips warningTips = checker.verify(User.builder().build());
        return warningTips;
    }
}
