package com.yukms.easy.test.mock.junit;

import java.lang.reflect.Method;

import com.yukms.easy.test.mock.MockConfig;
import lombok.Getter;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.springframework.util.ReflectionUtils;

/**
 * @author yukms 763803382@qq.com 2019/7/1 11:46
 */
@Getter
public class TestClassAndMethod extends TestWatcher {
    private Description description;
    private Class<?> clazz;
    private Method method;

    @Override
    protected void starting(Description description) {
        Class<?> testClass = description.getTestClass();
        this.clazz = testClass;
        this.method = ReflectionUtils.findMethod(testClass, description.getMethodName());
        this.description = description;
    }
}
