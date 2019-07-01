package com.yukms.easy.test.mock.mock;

import java.lang.reflect.Method;

import com.yukms.easy.test.mock.util.AspectJUtils;
import com.yukms.easy.test.mock.util.DataRecordUtils;
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
public class MockDataAspectJ {
    @Around("(@within(org.springframework.stereotype.Service)"//
        + "|| @within(org.springframework.stereotype.Component)"//
        + "|| @within(org.springframework.stereotype.Repository))")
    protected Object mockAround(ProceedingJoinPoint point) throws Throwable {
        Method method = AspectJUtils.getMethod(point);
        Object result;
        if (DataMocker.isGetMockData(method)) {
            log.info("获取Mock数据：" + point.getTarget().getClass().getSimpleName() + "." + method.getName());
            result = DataMocker.getData(method, point.getArgs());
        } else {
            try {
                result = point.proceed();
            } catch (Throwable e) {
                DataRecordUtils.recordData(point, e);
                throw e;
            }
        }
        DataRecordUtils.recordData(point, result);
        return result;
    }
}
