package com.mojota.succulent.utils;

import com.mojota.succulent.entity.ResponseInfo;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 *
 * @author jamie
 * @date 18-1-24
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler()
    ResponseInfo handleException(Exception e) {
        if (e instanceof CustomException) {
            return ResponseUtil.failure(((CustomException) e).getCode(), e
                    .getMessage());
        } else if (e instanceof BusinessException) {
            return ResponseUtil.failure(((BusinessException) e).getCode(), (
                    (BusinessException) e).getMsg());
        } else {
            e.printStackTrace();
            return ResponseUtil.failure(CodeConstants.CODE_SYSTEM_ERROR, e
                    .getMessage());//系统错误
        }
    }
}
