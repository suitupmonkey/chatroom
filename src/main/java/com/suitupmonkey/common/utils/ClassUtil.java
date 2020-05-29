package com.suitupmonkey.common.utils;

import cn.hutool.core.collection.CollectionUtil;
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
            boolean isString = ClassUtil.isString(field);
            boolean isCollection = ClassUtil.isCollection(field);
            Object value = field.get(object);
            //从null, string,集合判断是否为空.
            boolean nullValue = value == null;
            boolean stringIsEmpty = isString && StringUtils.isBlank(value.toString());
            boolean collectionIsEmpty = isCollection && CollectionUtil.isEmpty((Collection<?>) value);
            if(nullValue || stringIsEmpty || collectionIsEmpty){
                return true;
            }
        }
        return false;
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
                    || Modifier.isFinal(modifiers) || Modifier.isTransient(modifiers) ){
                continue;
            }
            field.setAccessible(true);
            fieldList.add(field);
        }
        for (Field field : fieldList) {
            int modifiers = field.getModifiers();
            //排除接口、static、final、transient
            if(Modifier.isInterface(modifiers) || Modifier.isStatic(modifiers)
                || Modifier.isFinal(modifiers) || Modifier.isTransient(modifiers) ){
                continue;
            }
            field.setAccessible(true);
            Object value = field.get(object);//属性值
            if(value != null && !StringUtils.isEmpty(value.toString())){
                containerMap.put(field.getName(),value);//不为空就添加到map
            }
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

    /**字段是否为String类型*/
    public static boolean isString(Field field){
        Class<?> sourceType = field.getType();
        Class<String> stringType = String.class;
        boolean isString = sourceType.equals(stringType);
        return isString;
    }

    /**字段是否为Byte类型*/
    public static boolean isByte(Field field){
        Class<?> sourceType = field.getType();
        Class<Byte> byteType = Byte.class;
        String typeName = field.getType().getName();
        boolean isByte = sourceType.equals(byteType) || typeName.equals("byte");
        return isByte;
    }

    /**字段是否为Short类型*/
    public static boolean isShort(Field field){
        Class<?> sourceType = field.getType();
        Class<Short> shortType = Short.class;
        String typeName = field.getType().getName();
        boolean isShort = sourceType.equals(shortType) || typeName.equals("short");
        return isShort;
    }

    /**字段是否为Integer类型*/
    public static boolean isInteger(Field field){
        Class<?> sourceType = field.getType();
        Class<Integer> intType = Integer.class;
        String typeName = field.getType().getName();
        boolean isInteger = sourceType.equals(intType) || typeName.equals("int");
        return isInteger;
    }

    /**字段是否为Long类型*/
    public static boolean isLong(Field field){
        Class<?> sourceType = field.getType();
        Class<Long> longType = Long.class;
        String typeName = field.getType().getName();
        boolean isLong = sourceType.equals(longType) || typeName.equals("long");
        return isLong;
    }

    /**字段是否为Character类型*/
    public static boolean isCharacter(Field field){
        Class<?> sourceType = field.getType();
        Class<Character> charType = Character.class;
        String typeName = field.getType().getName();
        boolean isCharacter = sourceType.equals(charType) || typeName.equals("character");
        return isCharacter;
    }

    /**字段是否为Boolean类型*/
    public static boolean isBoolean(Field field){
        Class<?> sourceType = field.getType();
        Class<Boolean> booleanType = Boolean.class;
        String typeName = field.getType().getName();
        boolean isBoolean = sourceType.equals(booleanType) || typeName.equals("boolean");
        return isBoolean;
    }

    /**字段是否为Double类型*/
    public static boolean isDouble(Field field){
        Class<?> sourceType = field.getType();
        Class<Double> doubleType = Double.class;
        String typeName = field.getType().getName();
        boolean isDouble = sourceType.equals(doubleType) || typeName.equals("double");
        return isDouble;
    }

    /**字段是否为Float类型*/
    public static boolean isFloat(Field field){
        Class<?> sourceType = field.getType();
        Class<Float> floatType = Float.class;
        String typeName = field.getType().getName();
        boolean isFloat = sourceType.equals(floatType) || typeName.equals("float");
        return isFloat;
    }

    /**字段是否为集合类型*/
    public static boolean isCollection(Field field) {
        Class<?> sourceType = field.getType();
        boolean isCollection = Collection.class.isAssignableFrom(sourceType);
        return isCollection;
    }


}
