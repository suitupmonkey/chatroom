package com.suitupmonkey.test;

import com.suitupmonkey.common.utils.VerificationUtil;
import com.suitupmonkey.enums.WarningTips;
import com.suitupmonkey.system.bean.User;

/**
 * author：louweiwei
 * date：2020/5/8 13:14
 * description：测试
 */
public class Test {
    public static void main(String[] args) throws IllegalAccessException {
        User lancelot = User.builder().username("").build();
        lancelot.setOperator(3);
        WarningTips warningTips = VerificationUtil.verify(lancelot);
        System.out.println(warningTips.getMessage());
    }


}
