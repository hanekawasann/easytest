package com.yukms.easy.test;

import com.yukms.easy.test.mock.data.DataMocker;
import com.yukms.easy.test.mock.junit.TestClassAndMethod;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Rule;

/**
 * @author yukms 763803382@qq.com 2019/7/1 11:29
 */
@Log4j2
public class BaseTest {
    private static final String PATH_SPLIT = "/";
    @Rule
    public TestClassAndMethod testClassAndMethod = new TestClassAndMethod();

    @Before
    public void initTestMethod() {
        Class<?> clazz = testClassAndMethod.getClazz();
        String pathPrefix = clazz.getResource("").getPath().replaceFirst(PATH_SPLIT, StringUtils.EMPTY);
        String className = clazz.getSimpleName();
        String methodName = testClassAndMethod.getMethod().getName();
        String mockDataDirPath = pathPrefix + className + PATH_SPLIT + methodName;
        log.info("测试数据路径" + mockDataDirPath);
        DataMocker.setMockData(mockDataDirPath);
    }
}
