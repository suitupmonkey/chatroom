package com.suitupmonkey.common.utils;

import com.suitupmonkey.common.constant.ConstantData;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;


public class ClassUtil implements Serializable {


    //判断类是否为空,类所有属性为空，类为空。
    public static boolean isEmpty(Object object) throws IllegalAccessException {
        Class<?> klass = object.getClass();

        //遍历属性，判断是否所有属性为空
        for (Field field : klass.getDeclaredFields()) {
            field.setAccessible(true);//允许访问
            Object value = field.get(object);
            //存在字段不为空，则类不为空。
            if(value != null && !StringUtils.isEmpty(value.toString())){
                return false;
            }
        }
        return true;
    }


    //判断类是否所有属性都有值，如果存在一个属性为null，或者""，返回false。
    public static boolean fullyOccupied(Object object) throws IllegalAccessException {
        Class<?> klass = object.getClass();
        //遍历属性，判断是否所有属性为空
        for (Field field : klass.getDeclaredFields()) {
            field.setAccessible(true);//允许访问
            Object value = field.get(object);
            //存在字段不为空，则类不为空。
            if(value == null || StringUtils.isEmpty(value.toString())){
                return false;
            }
        }
        return true;
    }

    /**
     * bean to map，含父类字段。
     * @param object
     * @return
     */
    public static Map<String, Object> beanToMap(Object object) throws IllegalAccessException {
        Map<String, Object> containerMap = new HashMap<>();//map容器
        Class<?> klass = object.getClass();
        List<Field> fieldList = new ArrayList(Arrays.asList(klass.getDeclaredFields()));//本类字段
        List<Field> parentFieldList = getParentFields(klass);//父类字段
        fieldList.addAll(parentFieldList);
        for (Field field:parentFieldList) {
            int modifiers = field.getModifiers();
            //排除接口、static、final、transient
            if(Modifier.isInterface(modifiers) || Modifier.isStatic(modifiers)
                    || Modifier.isFinal(modifiers) || Modifier.isTransient(modifiers) )continue;
            field.setAccessible(true);
            fieldList.add(field);
        }
        for (Field field : fieldList) {
            int modifiers = field.getModifiers();
            //排除接口、static、final、transient
            if(Modifier.isInterface(modifiers) || Modifier.isStatic(modifiers)
                || Modifier.isFinal(modifiers) || Modifier.isTransient(modifiers) )continue;
            field.setAccessible(true);
            Object value = field.get(object);//属性值
            if(value != null && !StringUtils.isEmpty(value.toString()))
                containerMap.put(field.getName(),value);//不为空就添加到map
        }
        return containerMap;
    }


    /**
     * @description: 获取父类字段
     * @author: louweiwei
     * @date: 2020/2/20
     */
    private static List<Field> getParentFields(Class<?> klass){
        Class<?> superclass = klass.getSuperclass();
        Field[] parentFields = superclass.getDeclaredFields();
        return Arrays.asList(parentFields);
    }


    /**字符串是否为数字
     * @description:
     * @author: louweiwei
     * @date: 2020/2/21
     */
    public static boolean isNumber(String string){
        return string.matches(ConstantData.NUMERIC);
    }

    /***
     * @description: 判断是否为手机号
     * @author: louweiwei
     * @date: 2020/4/7
     */
    public static boolean isPhone(String string){
        return string.matches(ConstantData.PHONE);
    }
    /***
     * @description: 判断是否为手机号
     * @author: louweiwei
     * @date: 2020/4/7
     */
    public static boolean isName(String string){
        return string.matches(ConstantData.CHINESE_CHARACTER) ||
                string.matches(ConstantData.CHINESE_CHARACTER);
    }

}
