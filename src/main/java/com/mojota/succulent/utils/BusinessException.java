package com.mojota.succulent.utils;

/**
 * 业务异常
 * @author jamie
 * @date 18-8-30
 */
public class BusinessException extends Exception {
    int code;
    String msg;

    public BusinessException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    public BusinessException(ResultEnum resultEnum){
        this.code = resultEnum.getCode();
        this.msg = resultEnum.getMsg();
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
