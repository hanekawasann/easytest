package com.yukms.easy.test.mock.data;

import java.lang.reflect.Method;

import com.yukms.easy.test.mock.data.DataMocker;
import com.yukms.easy.test.mock.util.AspectJUtils;
import com.yukms.easy.test.mock.util.MockDataUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
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
@Getter
@AllArgsConstructor
public class MockDataAspectJ {
    /** 是否记录数据 */
    private boolean recordData;

    @Around("(@within(org.springframework.stereotype.Service)"//
        + "|| @within(org.springframework.stereotype.Component)"//
        + "|| @within(org.springframework.stereotype.Repository))")
    protected Object mockAround(ProceedingJoinPoint point) throws Throwable {
        Method method = AspectJUtils.getMethod(point);
        Object result;
        log.info("获取Mock数据：" + point.getTarget().getClass().getSimpleName() + "." + method.getName());
        if (DataMocker.hasMockData()) {
            // 返回测试数据
            result = DataMocker.getMockData(method, point.getArgs());
        } else {
            try {
                // 返回真实数据
                result = point.proceed();
            } catch (Throwable e) {
                // 保存异常数据
                MockDataUtils.recordData(point, e);
                throw e;
            }
        }
        // 保存正常数据
        MockDataUtils.recordData(point, result);
        return result;
    }
}
