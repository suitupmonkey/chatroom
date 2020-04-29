package com.suitupmonkey.common.verify;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @program: chatroom
 * @description: 用于检查字段是否需要校验的注解
 * @author: louweiwei
 * @create: 2020-04-28 17:54
 **/
@Target({ ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Verify {
    public boolean nonNull() default false;
}
