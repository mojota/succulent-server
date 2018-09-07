package com.mojota.succulent.utils;

/**
 * @author jamie
 * @date 18-1-23
 */
public class CodeConstants {
    public static final int CODE_SUCCESS = 0;
    public static final int CODE_SYSTEM_ERROR = 1000; // 系统错误
    public static final int CODE_BUSINESS_ERROR = 2000;// 业务异常

    public static final int CODE_BUSINESS_ERROR_USER_EMPTY = 2001;
    public static final int CODE_BUSINESS_ERROR_EMAIL_FORMAT_INCORRECT = 2002;
    public static final int CODE_BUSINESS_ERROR_PWD_EMPTY = 2003;
    public static final int CODE_BUSINESS_ERROR_PWD_SHORT = 2004;


    public static final String MSG_BUSINESS_ERROR_USER_EMPTY = "用户名不能为空";
    public static final String MSG_BUSINESS_ERROR_EMAIL_FORMAT_INCORRECT = "邮箱格式不正确";
    public static final String MSG_BUSINESS_ERROR_PWD_EMPTY = "密码不能为空";
    public static final String MSG_BUSINESS_ERROR_PWD_SHORT = "密码最少为6位";
    public static final String MSG_BUSINESS_ERROR_USER_REPEAT = "用户已存在";
    public static final String MSG_BUSINESS_ERROR_USER_WRONG = "用户名或密码不正确";
    public static final String MSG_BUSINESS_DATA_NOT_FOUND = "未找到要修改的数据";
    public static final String MSG_BUSINESS_ERROR_USER_NOT_LOGIN = "用户未登录";


    public static final String MSG_BUSINESS_NOTE_NOT_FOUND = "未找到笔记";
    public static final String MSG_BUSINESS_DATA_EMPTY = "提交内容为空";
    public static final String MSG_BUSINESS_NOTE_DETAIL_NOT_FOUND = "未找到条目";

}
