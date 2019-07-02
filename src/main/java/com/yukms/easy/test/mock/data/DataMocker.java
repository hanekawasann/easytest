package com.yukms.easy.test.mock.data;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.yukms.easy.test.mock.util.MockDataUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Assert;

/**
 * 数据模拟器
 *
 * @author yukms 763803382@qq.com 2019/3/26.
 */
@Log4j2
public final class DataMocker {
    private static final ThreadLocal<LinkedList<File>> THREAD_LOCAL = ThreadLocal.withInitial(LinkedList::new);

    private DataMocker() {}

    /**
     * 设置测试数据
     *
     * @param mockDataDirPath 测试数据路径
     */
    public static void setMockData(String mockDataDirPath) {
        File mockDataDir = new File(mockDataDirPath);
        if (mockDataDir.exists()) {
            // 路径存在
            File[] files = mockDataDir.listFiles((dir, name) -> name.endsWith(".yaml"));
            if (files == null) {
                log.info("未找到测试数据");
                return;
            }
            Arrays.sort(files, File::compareTo);
            LinkedList<File> mockDataList = new LinkedList<>(Arrays.asList(files));
            THREAD_LOCAL.set(mockDataList);
            for (File file : mockDataList) {
                log.info("添加测试数据" + file.getName());
            }
        }
    }

    /**
     * 是否有测试数据
     *
     * @return true表示有测试数据
     */
    public static boolean hasMockData() {
        return CollectionUtils.isNotEmpty(THREAD_LOCAL.get());
    }

    /**
     * 获取mock数据
     *
     * @param method 请求方法
     * @param args   请求参数
     * @return mock数据
     */
    public static Object getMockData(Method method, Object[] args) throws IOException {
        File file = THREAD_LOCAL.get().poll();
        MockData mockData = MockDataUtils.loadData(file);
        checkMockData(method, args, mockData);
        // TODO: 2019/7/2
        return mockData;
    }

    private static void checkMockData(Method method, Object[] args, MockData mockData) {
        Assert.assertEquals("测试类不同", mockData.getMockClassName(), method.getDeclaringClass().getName());
        Assert.assertEquals("测试方法不同", mockData.getMethodName(), method.getName());
        List<?> expectedArgs = mockData.getArgs();
        Assert.assertEquals("测试参数个数不同", expectedArgs.size(), args.length);
        for (int i = 0; i < expectedArgs.size(); i++) {
            Assert.assertEquals("测试参数不同", expectedArgs.get(i), args[i]);
        }
    }

    /**
     * 清除mock数据
     */
    public static void clearData() {
        LinkedList<File> files = THREAD_LOCAL.get();
        if (CollectionUtils.isEmpty(files)) {
            log.info("测试数据全部使用");
            return;
        }
        for (File file : files) {
            log.info(file.getName() + "未被使用");
        }
    }
}
