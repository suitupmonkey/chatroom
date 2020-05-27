package com.suitupmonkey.enums;

/**
 * author：louweiwei
 * date：2020/5/8 13:19
 * description：WarningTips
 */
public enum WarningTips {
    OPERATE_SUCCEED(true,"操作成功"),
    WRONG_SCOPE(false, ""),
    FIELD_ABSENT(false, ""),
    EXCLUSIVE_FIELDS(false, ""),
    ;
    private Boolean success;
    private String message;


    WarningTips(Boolean success, String message){
        this.success = success;
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
