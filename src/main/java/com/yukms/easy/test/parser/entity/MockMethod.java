package com.yukms.easy.test.parser.entity;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * @author yukms 763803382@qq.com 2019/5/15 16:56
 */
@Data
public class MockMethod {
    /** 模拟的类 */
    private Class mockClazz;
    /** 模拟的方法 */
    private Method mockMethod;
    /** 输入参数 */
    private List<InoutData> methodArgs = new ArrayList<>();
    /** 预期结果 */
    private InoutData mockResult;
}
