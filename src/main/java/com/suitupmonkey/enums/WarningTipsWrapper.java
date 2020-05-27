package com.suitupmonkey.enums;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;

/**
 * author：louweiwei
 * date：2020/5/26 9:09
 * description：测试实体类转枚举
 */
@Data
@Builder
public class WarningTipsWrapper{
    private Boolean success;
    private String message;

    public WarningTipsWrapper(){}

    public WarningTipsWrapper(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }


    /**
     * 将实体类转换成 WarningTips 枚举，输出字段参数填写范围不正确的提示。
     * @param fieldScope 字段的参数范围
     * @param fieldName 字段名称
     * @return warningTips 转换后的枚举提示
     */
    public static WarningTips scopeWarning(String fieldName, Object value, ArrayList fieldScope){
        WarningTips warningTips = WarningTips.WRONG_SCOPE;
        //拼接消息
        String message = "参数" + fieldName + "应在" + JSONObject.toJSONString(fieldScope) + "范围内取值, 实际为: " + value+".";
        WarningTipsWrapper warningTipsWrapper = WarningTipsWrapper.builder().success(false).message(message).build();
        BeanUtil.copyProperties(warningTipsWrapper,warningTips);
        return warningTips;
    }

    /**
     * 将包装类转换成 WarningTips 枚举，输出字段为空的提示。
     * @param fieldName 字段名称
     * @return
     */
    public static WarningTips emptyWarning(String fieldName){
        WarningTips warningTips = WarningTips.FIELD_ABSENT;
        //拼接消息
        String message = "参数" + fieldName + "缺失";
        WarningTipsWrapper warningTipsWrapper = WarningTipsWrapper.builder().success(false).message(message).build();
        BeanUtil.copyProperties(warningTipsWrapper,warningTips);
        return warningTips;
    }

}
