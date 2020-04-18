package com.suitupmonkey.system.bean;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.io.Serializable;

@Data
@Builder
public class User implements Serializable {
    /**主键*/
    private String id;
    /**用户名*/
    private String username;
    /**密码*/
    private String password;
    /**头像地址*/
    private long head;
    @Tolerate
    public User(){}
}
