package com.mojota.succulent.utils;

/**
 * @author jamie
 * @date 18-1-24
 */
public class CustomException extends RuntimeException {

    int code;

    public CustomException(int code, String msg) {
        super(msg);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
