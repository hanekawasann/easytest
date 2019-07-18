package com.yukms.easy.test.mock.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author hudingpeng hudingpeng@souche.com 2019/7/16 18:03
 */
@Target( { ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface EasyTestMock {
    /** 默认保存运行时数据 */
    boolean isSaveData() default true;
}
