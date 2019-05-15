package com.yukms.easy.test.generator.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author yukms 763803382@qq.com 2019/5/15 17:09
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ExceptionInoutData extends InoutData {
    /** 异常类型 */
    private Class<? extends Throwable> clazz;
}
