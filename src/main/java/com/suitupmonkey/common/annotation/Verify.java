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
    /**
     * 是否需要校验
     * @return
     */
    boolean mandatory() default false;

    /**
     * int属性值的区间范围
     * 当前不能在使用注解时，检验使用者字段声明是否正确；
     * 如果字段类型声明为 整型数值 以外的其他类型，将跳过该校验。
     * @return
     */
    int[] intScope() default {};

    /**
     * string属性值的区间范围；
     * 当前不能在使用注解时，检验使用者字段声明是否正确；
     * 如果字段类型声明为 string 以外的其他类型，将跳过该校验。
     * @return
     */
    String[] stringScope() default {};
}
