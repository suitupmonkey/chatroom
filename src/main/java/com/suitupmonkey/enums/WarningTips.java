package com.suitupmonkey.enums;

/**
 * author：louweiwei
 * date：2020/5/8 13:19
 * description：WarningTips
 */
public enum WarningTips {
    OPERATE_SUCCEED(true,10000,"操作成功"),
    OPERATE_FAIL(false,10001,"操作失败"),
    USERNAME_ABSENT(false,10002,"用户名缺失","username"),
    PASSWORD_ABSENT(false,10003,"密码缺失","password"),
    HEAD_ABSENT(false,10004,"头像地址缺失","head"),
    PK_ABSENT(false,10004,"主键ID缺失","id"),
    LISTABSENT(false,10005,"缺少list参数","list"),
    ;
    private Boolean success;
    private Integer code;
    private String message;
    private String field;

    WarningTips(Boolean success, Integer code, String message, String field) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.field = field;
    }

    WarningTips(Boolean success, Integer code, String message){
        this.success = success;
        this.code = code;
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
