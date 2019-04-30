package com.yukms.easy.test.generator.entity.param;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * @author yukms 763803382@qq.com 2019/4/30 17:44
 */
@Data
public class TestGeneratorData {
    /** 被测试类 */
    private ClazzData testClazz;
    /** 被mock类 */
    private List<ClazzData> mockClazzs = new ArrayList<>();
}
