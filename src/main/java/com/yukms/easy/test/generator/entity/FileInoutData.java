package com.yukms.easy.test.generator.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author yukms 763803382@qq.com 2019/5/15 16:50
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FileInoutData extends InoutData {
    /** 文件路径 */
    private String path;
}
