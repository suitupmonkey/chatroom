package com.suitupmonkey.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.suitupmonkey.common.utils.VerificationUtil;
import com.suitupmonkey.enums.WarningTips;
import com.suitupmonkey.system.bean.User;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * author：louweiwei
 * date：2020/5/8 13:14
 * description：测试
 */
public class VerificationTest {
    public static void main(String[] args) throws IllegalAccessException {
//        User lancelot = User.builder().username("").build();
//        lancelot.setOperator(3);
//        WarningTips warningTips = VerificationUtil.verify(lancelot);
//        System.out.println(warningTips.getMessage());

        JwtBuilder builder= Jwts.builder()
                .setId("888")   //设置唯一编号
                .setSubject("小白")//设置主题  可以是JSON数据
                .setIssuedAt(new Date())//设置签发日期
                .signWith(SignatureAlgorithm.HS256,"hahaha");//设置签名 使用HS256算法，并设置SecretKey(字符串)
        //构建 并返回一个字符串
        System.out.println( builder.compact() );
    }


}
