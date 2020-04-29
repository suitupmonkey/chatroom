package com.suitupmonkey.common.aop;

import com.suitupmonkey.common.utils.ClassUtil;
import com.suitupmonkey.common.verify.Verify;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
    public Map doBefore(JoinPoint joinPoint){
        Object[] arguments = joinPoint.getArgs();
        log.info("入参校验，接口参数为-> {}",arguments);
        try {
            for (Object argument : arguments) {
                /**实体类具体校验逻辑*/
                boolean verified = verifyEntity(argument);
            }
        }catch (Exception e){
            log.error("校验发生异常, 校验失败. -> {}, 接口参数为-> {}" + e.getMessage(),arguments);
            log.error("异常详细信息为-> {}" + e.getStackTrace());
        }
        Map<String,Object> msg = new HashMap<>();
        msg.put("success",true);
        return msg;
    }

    /**实体类校验*/
    private boolean verifyEntity(Object object) throws IllegalAccessException, InstantiationException {
        Class<?> clazz = object.getClass();
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Verify annotation = field.getAnnotation(Verify.class);
            boolean needToCheck = annotation != null && annotation.nonNull();
            if (!needToCheck) continue;
            Object value = field.get(object);
            log.info("字段名称-> {}, 字段值-> {}", fieldName, value);
            /**字符串字段为空*/
            if (ClassUtil.isString(field)) {
                boolean strEmpty = StringUtils.isEmpty(value.toString()) || StringUtils.isBlank(value.toString());
                if (strEmpty) {
                    System.out.println("字符串字段空了");
                }
            }
            /**集合字段为空*/
            if (ClassUtil.isCollection(field)) {
                boolean collectionIsEmpty = CollectionUtils.isEmpty((Collection) value);
                if (collectionIsEmpty) {
                    System.out.println("集合为空");
                }
            }
            /**其他类型字段为空*/
            if (value == null) {
                System.out.println("其他类型字段为空");
            }
        }
        return false;
    }


}
