package com.yukms.easy.test.mock.util;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.util.ReflectionUtils;

/**
 * @author yukms 763803382@qq.com 2019/6/28 11:35
 */
public final class AspectJUtils {
    public static Method getMethod(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return ReflectionUtils
            .findMethod(joinPoint.getTarget().getClass(), signature.getName(), signature.getParameterTypes());
    }

    private AspectJUtils() { }
}
