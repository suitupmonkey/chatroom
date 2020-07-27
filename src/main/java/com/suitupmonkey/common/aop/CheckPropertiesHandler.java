package com.suitupmonkey.common.aop;

import com.suitupmonkey.common.utils.VerificationUtil;
import com.suitupmonkey.enums.WarningTips;
import com.suitupmonkey.system.bean.User;
import org.springframework.stereotype.Component;

/**
 * @author：louweiwei
 * @date：2020/7/27 15:02
 * @description：校验
 */
@Component
public class CheckPropertiesHandler {

    public WarningTips verify(User user) throws IllegalAccessException {
        WarningTips warningTips = VerificationUtil.verify(user);
        return warningTips;
    }
}
