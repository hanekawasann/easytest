package com.yukms.easy.test;

import com.yukms.easy.test.mock.junit.TestClassAndMethod;
import org.junit.Before;
import org.junit.Rule;

/**
 * @author yukms 763803382@qq.com 2019/7/1 11:29
 */
public class BaseTest {
    @Rule
    public TestClassAndMethod testClassAndMethod = new TestClassAndMethod();

    @Before
    public void initTestMethod() {

    }
}
