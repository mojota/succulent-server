package com.mojota.succulent.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.mojota.succulent.utils.CodeConstants;

import java.util.List;

/**
 * @author jamie
 * @date 18-1-23
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseInfo<T> {
    @JsonView(JsonRespView.baseView.class)
    private int code;
    @JsonView(JsonRespView.baseView.class)
    private String msg;
    @JsonView(JsonRespView.baseView.class)
    private T data;
    @JsonView(JsonRespView.baseView.class)
    private List<T> list;

    public ResponseInfo() {
        code = CodeConstants.CODE_SUCCESS;
        msg = "";
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
