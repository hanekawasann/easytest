package com.yukms.easy.test.generator.entity.output;

import java.util.List;

import com.yukms.easy.test.generator.entity.param.TestEnv;
import com.yukms.easy.test.generator.entity.param.TestGeneratorData;
import lombok.Data;

/**
 * @author yukms 763803382@qq.com 2019/4/30 18:26
 */
@Data
public class OutputField {
    /** package */
    private String pakg;
    /** 导入类 */
    private List<Class<?>> imports;
    /** 测试环境 */
    private TestEnv env;
    /** 测试数据 */
    private List<TestGeneratorData> data;
}
