package com.yukms.easy.test.generator.entity.param;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * @author yukms 763803382@qq.com 2019/4/30 17:53
 */
@Data
public class ClazzData {
    /** 方法 */
    private Method method;
    /** 入参 */
    private List<Object> params = new ArrayList<>();
    /** 出参 */
    private Object result;
}
