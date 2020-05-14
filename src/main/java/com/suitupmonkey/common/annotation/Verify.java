package com.suitupmonkey.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * author：louweiwei
 * date：2020/5/8 13:06
 * description：用于入参校验
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Verify {
    /**是否需要校验*/
    boolean mandatory() default false;
    /**int属性的区间范围*/
    int[] intScope() default {1,2};
    /**int属性的区间范围*/
    String[] StringScope() default {"1","2"};
}
