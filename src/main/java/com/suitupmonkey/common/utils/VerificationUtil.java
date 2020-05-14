package com.suitupmonkey.common.utils;

import com.baomidou.mybatisplus.toolkit.CollectionUtils;
import com.suitupmonkey.common.annotation.Verify;
import com.suitupmonkey.enums.WarningTips;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.Collection;

/**
 * author：louweiwei
 * date：2020/5/8 13:38
 * description：参数校验工具
 */
public class VerificationUtil {

    /**校验*/
    public static WarningTips verify(Object object) throws IllegalAccessException {
        WarningTips warningTips = WarningTips.OPERATE_SUCCEED;
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            boolean isBoolean = ClassUtil.isBoolean(field);
            /**不校验boolean*/
            if(isBoolean){continue;}
            Verify verify = field.getAnnotation(Verify.class);
            boolean mandatory = verify != null && verify.mandatory() == true;
            /**只校验需要的字段*/
            if(!mandatory) { continue; }
            Object value = field.get(object);
            /**对字段校验*/
            warningTips = someFieldMayBeEmpty(field, value);
            if(!warningTips.getSuccess()) {return warningTips;}
        }
        return warningTips;
    }


    /**具体的校验执行*/
    private static WarningTips someFieldMayBeEmpty(Field field, Object value) {
        boolean isString = ClassUtil.isString(field);
        boolean isCollection = ClassUtil.isCollection(field);
        String fieldName = field.getName();
        for (WarningTips warningTips : WarningTips.values()) {
            String fieldNameInEnum = warningTips.getField();
            /**字段名称不匹配*/
            if(!fieldName.equals(fieldNameInEnum)) { continue; }
            /**跳过没有字段名称的枚举*/
            if(StringUtils.isEmpty(fieldNameInEnum)) {continue;}
            /**开始非空校验*/
            boolean nullValue = value == null;
            boolean stringIsEmpty = isString && StringUtils.isBlank(value.toString());
            boolean collectionIsEmpty = isCollection && CollectionUtils.isEmpty((Collection<?>) value);
            if(nullValue || stringIsEmpty || collectionIsEmpty){
                return warningTips;
            }
        }
        return WarningTips.OPERATE_SUCCEED;
    }
}
