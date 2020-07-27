package com.suitupmonkey.common.exception;

import com.suitupmonkey.enums.WarningTips;

/**
 * @author：louweiwei
 * @date：2020/7/27 15:09
 * @description：校验异常
 */
public class WarningException extends RuntimeException{

    private String message;

    public WarningException(WarningTips warningTip){
        this.message = warningTip.getMessage();
    }
}
