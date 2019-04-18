package com.suitupmonkey.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.lang.reflect.Field;


public class ClassUtil implements Serializable {

    //判断类是否为空,类所有属性为空，类为空。
    public static boolean isEmpty(Object object) throws IllegalAccessException {
        Class<?> klass = object.getClass();

        //遍历属性，判断是否所有属性为空
        for (Field field : klass.getDeclaredFields()) {
            field.setAccessible(true);//允许访问
            Object value = field.get(object);
            //存在字段不为空，则类不为空。
            if(value != null || StringUtils.isEmpty(value.toString())){
                return false;
            }
        }
        return true;
    }
}
