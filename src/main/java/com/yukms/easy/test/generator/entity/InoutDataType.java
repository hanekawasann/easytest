package com.yukms.easy.test.generator.entity;

/**
 * @author yukms 763803382@qq.com 2019/5/15 16:47
 */
public enum InoutDataType {
    /** 任意数据：仅用于输入 */
    ANY,
    /** 基本类型及其包装类和String */
    VALUE,
    /** 序列化 */
    OBJ_STREAM,
    /** JSON */
    JSON,
    /** 异常：仅用于输出 */
    EXCEPTION;
}
