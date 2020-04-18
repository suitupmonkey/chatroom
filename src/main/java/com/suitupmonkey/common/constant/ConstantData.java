package com.suitupmonkey.common.constant;

/**
 * @program:
 * @description: 全局常量
 * @author: louweiwei
 * @create: 2020-03-13 10:13
 */
public class ConstantData {

    /**数字*/
    public static final String NUMERIC = "^(-)?[1-9]+.[0-9]+";
    /**汉字姓名*/
    public static final String CHINESE_CHARACTER = "[^\\x00-\\xff]{1,5}";
    /**英文姓名*/
    public static final String ENGLISH_CHARACTER = "([a-zA-Z]{1,20}[ ]{0,}){1,5}";
    /**电话号码正则*/
    public static final String PHONE = "^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|145|147|149|173|175|176|177|178)+\\d{8})$";

}
