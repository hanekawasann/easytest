package com.yukms.easy.test.mock.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author yukms 763803382@qq.com 2019/3/26.
 */
public final class ClassUtil {

    private ClassUtil() { }

    /**
     * 类是否覆盖某方法
     *
     * @param clazz  类
     * @param method 方法
     * @return true表示覆盖
     */
    public static boolean isOverrideMethod(Class clazz, Method method) {
        Class<?> declaringClass = method.getDeclaringClass();
        if (declaringClass.isAssignableFrom(clazz) || isImplementSameInterface(clazz, declaringClass)) {
            try {
                return isSameSignature(clazz.getDeclaredMethod(method.getName(), method.getParameterTypes()), method);
            } catch (NoSuchMethodException e) {
                return false;
            }
        }
        return false;
    }

    /**
     * 一个类与另一个类是否实现同一个接口
     *
     * @param clazz      类
     * @param otherClazz 另一个类
     * @return true表示实现了同一个接口
     */
    private static boolean isImplementSameInterface(Class clazz, Class otherClazz) {
        List<Class> otherSuperClazzs = getSuperClassAndInterfaces(otherClazz);
        for (Class superClazz : getSuperClassAndInterfaces(clazz)) {
            if (superClazz.isInterface() && otherSuperClazzs.contains(superClazz)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取指定类的所有父类与接口（接口可能会重复）
     *
     * @param clazz 类
     * @return 结果
     */
    private static List<Class> getSuperClassAndInterfaces(Class clazz) {
        if (null == clazz) {
            return Collections.EMPTY_LIST;
        }
        List<Class> clazzs = new ArrayList<>();
        Class[] interfaces = clazz.getInterfaces();
        clazzs.addAll(Arrays.asList(interfaces));
        for (Class interfaceClass : interfaces) {
            clazzs.addAll(getSuperClassAndInterfaces(interfaceClass));
        }
        Class superclass = clazz.getSuperclass();
        if (null != superclass) {
            clazzs.add(superclass);
            clazzs.addAll(getSuperClassAndInterfaces(superclass));
        }
        return clazzs;
    }

    /**
     * 两个方法签名是否相同
     *
     * @param method      方法
     * @param otherMethod 另一个方法
     * @return true表示签名相同
     */
    private static boolean isSameSignature(Method method, Method otherMethod) {
        boolean noNull = null != method && null != otherMethod;
        if (noNull) {
            boolean sameReturnType = method.getReturnType().equals(otherMethod.getReturnType());
            boolean sameName = method.getName().equals(otherMethod.getName());
            boolean sameParamterType = Arrays.asList(method.getParameterTypes())
                .equals(Arrays.asList(otherMethod.getParameterTypes()));
            return sameReturnType && sameName && sameParamterType;
        }
        return false;
    }
}
