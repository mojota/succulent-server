package com.mojota.succulent.utils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * 全局敏感信息拦截
 *
 * @author jamie
 * @date 19-1-15
 */
@Aspect
@Component
public class SensitiveWordsAspect {

    @Pointcut("execution(public * com.mojota.succulent.controller.*.*(..))")
    public void pointcut() {
    }

    @Before("pointcut()")
    public void doBefore(JoinPoint joinPoint) throws BusinessException {
        Object[] args = joinPoint.getArgs();
        if (args != null) {
            for (Object arg : args) {
                if (arg != null) {
                    String argStr = String.valueOf(arg);
                    if (ToolUtil.containSensitiveWords(argStr)) {
                        throw new BusinessException(ResultEnum.BUSINESS_ERROR_SENSITIVE_WORDS);
                    }
                }
            }
        }
    }

}