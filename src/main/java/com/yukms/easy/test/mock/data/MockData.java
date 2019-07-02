package com.yukms.easy.test.mock.data;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * @author yukms 763803382@qq.com 2019/6/28 10:31
 */
@Data
public class MockData {
    /** mock的类 */
    private String mockClassName;
    /** mock的方法 */
    private String methodName;
    /** 参数 */
    private List<?> args = new ArrayList<>();
    /** 结果 */
    private Object result;
    /** 异常 */
    private String expectedClassName;
}
