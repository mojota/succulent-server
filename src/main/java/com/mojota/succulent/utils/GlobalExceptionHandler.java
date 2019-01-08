package com.mojota.succulent.utils;

import com.mojota.succulent.dto.ResponseInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler()
    ResponseInfo handleException(Exception e) {
        e.printStackTrace();
        logger.error(e.getMessage());
        if (e instanceof CustomException) {
            return ResponseUtil.failure(((CustomException) e).getCode(), e
                    .getMessage());
        } else if (e instanceof BusinessException) {
            return ResponseUtil.failure(((BusinessException) e).getCode(), (
                    (BusinessException) e).getMsg());
        } else {
            return ResponseUtil.failure(ResultEnum.SYSTEM_ERROR);//系统错误
        }
    }
}
