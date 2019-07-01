package com.yukms.easy.test.generator.entity;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * @author yukms 763803382@qq.com 2019/5/15 18:29
 */
@Data
public class UnitTestClass {
    /** package */
    private String packagePath;
    /** import */
    private String importPath;
    /** 类名 */
    private String className;
    //private List<>
    /** 方法 */
    List<UnitTestMethod> methods = new ArrayList<>();
}
