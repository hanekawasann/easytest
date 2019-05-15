package com.yukms.easy.test.parser.entity;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * @author yukms 763803382@qq.com 2019/5/15 16:28
 */
@Data
public class TestMethod {
    /** 方法名 */
    private String name;
    /** 被测试方法 */
    private Method method;
    /** 输入参数 */
    private List<InoutData> methodArgs = new ArrayList<>();
    /** 预期结果 */
    private InoutData expectResult;
    /** 模拟的方法 */
    private List<MockMethod> mockMethods = new ArrayList<>();
}
