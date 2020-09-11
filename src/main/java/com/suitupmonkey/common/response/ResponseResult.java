package com.suitupmonkey.common.response;

import lombok.Data;
import org.apache.poi.ss.formula.functions.T;

/**
 * author：louweiwei
 * date：2020/5/8 13:33
 * description：通用返回结果
 */
@Data
public class ResponseResult {
    /**消息编码*/
    private Integer code;
    /**消息*/
    private String message;
    /**处理结果*/
    private Boolean succeeded;
    /**data*/
    private T data;
}
