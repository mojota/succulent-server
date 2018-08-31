package com.mojota.succulent.utils;

import com.mojota.succulent.entity.ResponseInfo;

import java.util.Collection;
import java.util.Map;

/**
 * 返回响应处理
 * @author jamie
 * @date 18-1-24
 */
public class ResponseUtil {

    public static ResponseInfo success(Object o) {
        ResponseInfo respInfo = new ResponseInfo();
        respInfo.setCode(CodeConstants.CODE_SUCCESS);
        respInfo.setMsg("success");
        respInfo.setData(o);
//        if (o != null) {
//            if (o instanceof Collection || o instanceof Map) {
//                ResponseInfo.List list = respInfo.new List();
//                list.setList(o);
//                respInfo.setData(list);
//            } else {
//                respInfo.setData(o);
//            }
//        }
        return respInfo;
    }

    public static ResponseInfo failure(int code, String msg) {
        ResponseInfo respInfo = new ResponseInfo();
        respInfo.setCode(code);
        respInfo.setMsg(msg);
        return respInfo;
    }
}
