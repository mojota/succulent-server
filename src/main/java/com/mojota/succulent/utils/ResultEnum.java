package com.mojota.succulent.utils;

/**
 * @author jamie
 * @date 18-10-15
 */
public enum ResultEnum {

    SUCCESS(0, "成功"),
    SYSTEM_ERROR(1000, "系统错误"),
    BUSINESS_ERROR(2000, "业务异常"),

    BUSINESS_ERROR_USER_EMPTY(2001, GlobalConstants.MSG_BUSINESS_ERROR_USER_EMPTY),
    BUSINESS_ERROR_EMAIL_FORMAT_INCORRECT(2002,
            GlobalConstants.MSG_BUSINESS_ERROR_EMAIL_FORMAT_INCORRECT),
    BUSINESS_ERROR_PWD_EMPTY(2003, GlobalConstants.MSG_BUSINESS_ERROR_PWD_EMPTY),
    BUSINESS_ERROR_PWD_SHORT(2004, GlobalConstants.MSG_BUSINESS_ERROR_PWD_SHORT),
    BUSINESS_ERROR_USER_REPEAT(2005, GlobalConstants.MSG_BUSINESS_ERROR_USER_REPEAT),
    BUSINESS_ERROR_USER_WRONG(2006, GlobalConstants.MSG_BUSINESS_ERROR_USER_WRONG),
    BUSINESS_ERROR_USER_NOT_LOGIN(2007,
            GlobalConstants.MSG_BUSINESS_ERROR_USER_NOT_LOGIN),
    BUSINESS_DATA_NOT_FOUND(2008, GlobalConstants.MSG_BUSINESS_DATA_NOT_FOUND),
    BUSINESS_NOTE_NOT_FOUND(2009, GlobalConstants.MSG_BUSINESS_NOTE_NOT_FOUND),
    BUSINESS_DATA_EMPTY(2010, GlobalConstants.MSG_BUSINESS_DATA_EMPTY);


    private int code;
    private String msg;

    ResultEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
