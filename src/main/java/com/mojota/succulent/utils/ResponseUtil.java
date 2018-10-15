package com.mojota.succulent.utils;

import com.mojota.succulent.dto.PageInfo;
import com.mojota.succulent.dto.ResponseInfo;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 返回响应处理
 *
 * @author jamie
 * @date 18-1-24
 */
public class ResponseUtil {


    public static ResponseInfo success(Object o, Pageable pageable) {
        ResponseInfo respInfo = new ResponseInfo();
        respInfo.setCode(ResultEnum.SUCCESS.getCode());
        respInfo.setMsg(ResultEnum.SUCCESS.getMsg());
        if (pageable != null) {
            PageInfo pageInfo = new PageInfo();
            pageInfo.setPageSize(pageable.getPageSize());
            pageInfo.setPageNumber(pageable.getPageNumber());
            respInfo.setPageInfo(pageInfo);
        }
        if (o != null && o instanceof List) {
            respInfo.setList((List) o);
        } else {
            respInfo.setData(o);
        }
        return respInfo;
    }

    public static ResponseInfo success(Object o) {
        return success(o, null);
    }

    public static ResponseInfo failure(ResultEnum resultEnum) {
        ResponseInfo respInfo = new ResponseInfo();
        respInfo.setCode(resultEnum.getCode());
        respInfo.setMsg(resultEnum.getMsg());
        return respInfo;
    }

    public static ResponseInfo failure(int code, String message) {
        ResponseInfo respInfo = new ResponseInfo();
        respInfo.setCode(code);
        respInfo.setMsg(message);
        return respInfo;
    }
}
