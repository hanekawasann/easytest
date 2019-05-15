package com.yukms.easy.test.parser.entity;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * @author yukms 763803382@qq.com 2019/4/30 17:44
 */
@Data
public class UnitTestXml {
    /** 单元测试名称 */
    private String testName;
    /** 被测试类 */
    private Class testClazz;
    /** 测试方法 */
    List<TestMethod> testMethods = new ArrayList<>();
}
