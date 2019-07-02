package com.yukms.easy.test;

import java.lang.reflect.Method;

import org.junit.Test;
import org.springframework.util.ReflectionUtils;

/**
 * @author yukms 763803382@qq.com 2019/7/1 10:56
 */
public class TestDemo {
    @Test
    public void test_01() throws NoSuchMethodException {
        System.out.println(getTestStackTraceElement());
    }

    public static StackTraceElement getTestStackTraceElement() throws NoSuchMethodException {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for (StackTraceElement element : stackTrace) {
            if (isTestStackTraceElement(element)) {
                return element;
            }
        }
        throw new NoSuchMethodException("未找到Junit测试方法");
    }

    private static boolean isTestStackTraceElement(StackTraceElement element) {
        try {
            Class<?> clazz = Class.forName(element.getClassName());
            Method method = ReflectionUtils.findMethod(clazz, element.getMethodName());
            if (method != null) {
                if (method.getAnnotation(Test.class) != null) {
                    return true;
                }
            }
        } catch (ClassNotFoundException ignored) { }
        return false;
    }
}
