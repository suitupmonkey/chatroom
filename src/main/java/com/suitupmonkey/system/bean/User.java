package com.suitupmonkey.system.bean;

import com.suitupmonkey.common.annotation.Verify;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("user")
@Data
@Builder
public class User implements Serializable {
    @Verify(mandatory = true, intScope = {3,4,5})
    private Integer operator;
    /**主键*/
    private String id;
    /**用户名*/
    @Verify(mandatory = true)
    private String username;
    /**密码*/
    private String password;
    /**头像地址*/
    private String head;
    @Tolerate
    public User(){}
}
