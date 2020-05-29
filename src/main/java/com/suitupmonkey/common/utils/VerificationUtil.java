package com.suitupmonkey.common.utils;

import cn.hutool.core.collection.CollectionUtil;
import com.suitupmonkey.common.annotation.Verify;
import com.suitupmonkey.enums.WarningTips;
import com.suitupmonkey.enums.WarningTipsWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

/**
 * author：louweiwei
 * date：2020/5/8 13:38
 * description：参数校验工具
 */
@Slf4j
public class VerificationUtil {

    /**
     * 用于对接口调用时，对入参实体类VO的校验。
     * @param object 入参VO类
     * @return
     * @throws IllegalAccessException
     */
    public static WarningTips verify(Object object) throws IllegalAccessException {
        WarningTips warningTips = WarningTips.OPERATE_SUCCEED;
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Verify verify = field.getAnnotation(Verify.class);
            //跳过不需要校验的字段
            if(verify == null){ continue; }
            //字段值
            Object value = field.get(object);
            //非空校验
            if(verify.mandatory()) {
                warningTips = someFieldCouldBeEmpty(field, value);
                if(!warningTips.getSuccess()) {return warningTips;}
            }
            //范围校验
            String[] stringScope = verify.stringScope();
            Integer[] intScope = ArrayUtils.toObject(verify.intScope());
            boolean restrictiveValue = (stringScope.length > 0 || intScope.length > 0)
                                     && (ClassUtil.isString(field) || ClassUtil.isInteger(field));
            if(restrictiveValue){
                ArrayList scope = stringScope.length > 0 ? CollectionUtil.newArrayList(stringScope) : CollectionUtil.newArrayList(intScope);;
                warningTips = fieldScopeMayBeWrong(field, value, scope);
                //校验失败返回提示
                if(!warningTips.getSuccess()) return warningTips;
            }
        }
        return warningTips;
    }

    /**
     * 范围校验
     * 当前不能在定义注解的时候，限制对应的字段类型。
     * 这个方法用来检验，某个字段值是否在一个给定的string[]或者int[]中；
     * 如果目标字段不是 string 或者 int(integer)，则不做校验。
     * @param field 字段
     * @param value 字段值
     * @param scope 字段限定的范围
     * @return
     */
    private static WarningTips fieldScopeMayBeWrong(Field field, Object value, ArrayList scope) {
        WarningTips warningTips = WarningTips.OPERATE_SUCCEED;
        //字段不是string或者int(integer)类型，不做校验。
        if(!ClassUtil.isString(field) && !ClassUtil.isInteger(field)){
            return warningTips;
        }
        String fieldName = field.getName();
        //参数是否在限定范围
        boolean inScope = scope.contains(value);
        //通过包装类对 WarningTips 做转换，输出提示。
        warningTips = inScope ? warningTips : WarningTipsWrapper.scopeWarning(fieldName, value, scope);
        return warningTips;
    }

    /**
     * 非空校验, 通过枚举包装类动态生成提示.
     * @param field 字段
     * @param value 字段值
     * @return
     */
    private static WarningTips someFieldCouldBeEmpty(Field field, Object value) {
        WarningTips warningTips = WarningTips.OPERATE_SUCCEED;
        boolean isString = ClassUtil.isString(field);
        boolean isCollection = ClassUtil.isCollection(field);
        String fieldName = field.getName();
        //开始非空校验
        boolean nullValue = value == null;
        //null直接返回
        if(nullValue) return WarningTipsWrapper.emptyWarning(fieldName);
        boolean stringIsEmpty = isString && StringUtils.isBlank(value.toString());
        boolean collectionIsEmpty = isCollection && CollectionUtil.isEmpty((Collection<?>) value);
        //单独对字符串和集合判空，这里判断的不好，后面需要优化。
        if(stringIsEmpty || collectionIsEmpty){
            warningTips =  WarningTipsWrapper.emptyWarning(fieldName);
        }
        return warningTips;
    }


}
