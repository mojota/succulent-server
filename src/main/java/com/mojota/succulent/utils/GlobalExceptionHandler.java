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
//        e.printStackTrace();
        StringBuffer stsb = new StringBuffer("异常堆栈:");
        for (int i = 0; i < e.getStackTrace().length; i++) {
            if (i <= 10) { //只打印最近n行
                stsb.append("\n").append(e.getStackTrace()[i].toString());
            }
        }
        logger.error(stsb.toString());
        logger.error("错误描述:" + e.getLocalizedMessage());
        if (e instanceof BusinessException) {
            logger.error("BusinessException " + String.valueOf(((BusinessException) e).getCode()) + ":" + ((BusinessException) e).getMsg());
            return ResponseUtil.failure(((BusinessException) e).getCode(), (
                    (BusinessException) e).getMsg());
        } else {
            logger.error("Exception " + ResultEnum.SYSTEM_ERROR.getCode() + ":" + ResultEnum.SYSTEM_ERROR.getMsg());
            return ResponseUtil.failure(ResultEnum.SYSTEM_ERROR);//系统错误
        }
    }
}
