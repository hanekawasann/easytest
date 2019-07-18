package com.yukms.easy.test.mock.data;

import java.util.concurrent.atomic.AtomicInteger;

import lombok.Data;

/**
 * @author yukms 763803382@qq.com 2019/6/28 15:09
 */
@Data
public class DataRecord {
    /** 序号 */
    private AtomicInteger num = new AtomicInteger(1);
    /** 文件夹名 */
    private String folderPath;
}
