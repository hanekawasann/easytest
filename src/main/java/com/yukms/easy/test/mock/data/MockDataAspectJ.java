package com.yukms.easy.test.mock.data;

import java.lang.reflect.Method;

import com.yukms.easy.test.mock.MockConfig;
import com.yukms.easy.test.mock.util.AspectJUtils;
import com.yukms.easy.test.mock.util.MockDataUtils;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * mock切面
 *
 * @author yukms 763803382@qq.com 2019/3/26.
 */
@Log4j2
@Aspect
@AllArgsConstructor
public class MockDataAspectJ {
    @Around("(@within(org.springframework.stereotype.Service)"//
        + "|| @within(org.springframework.stereotype.Component)"//
        + "|| @within(org.springframework.stereotype.Repository))")
    protected Object mockAround(ProceedingJoinPoint point) throws Throwable {
        Method method = AspectJUtils.getMethod(point);
        Object result;
        if (DataMocker.needGetMockData(method, point.getArgs())) {
            log.info("获取Mock数据 " + point.getTarget().getClass().getSimpleName() + "." + method.getName());
            // 返回测试数据
            result = DataMocker.getMockData();
        } else {
            // 请求真实数据
            result = point.proceed();
        }
        if (MockConfig.isSaveData()) {
            MockDataUtils.recordData(point, result);
        }
        return result;
    }
}
