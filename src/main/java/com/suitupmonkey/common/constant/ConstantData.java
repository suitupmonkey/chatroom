package com.suitupmonkey.common.constant;

/**
 * @program: chatroom
 * @description: 常量
 * @author: louweiwei
 * @create: 2020-04-30 13:32
 */
public class ConstantData {

    /**邮箱正则*/
    public static final String EMAIL = "^[\\w]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
    /**团队名称-10个汉字以内*/
    public static final String TEAM_NAME_CH = "[^\\x00-\\xff]{1,10}";
    /**团队名称-少于25个英文字符,中间可以每个间隔可以用一个空格.*/
    public static final String TEAM_NAME_EN = "(([a-zA-Z][ ]{0,1}){0,15})";
    /**汉字姓名*/
    public static final String CHINESE_CHARACTER = "[^\\x00-\\xff]{1,5}";
    /**英文姓名*/
    public static final String ENGLISH_CHARACTER = "(([a-zA-Z][ ]{0,1}){0,20})";
    /**电话号码正则*/
    public static final String PHONE = "^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|145|147|149|173|175|176|177|178)+\\d{8})$";

}
