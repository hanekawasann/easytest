package com.yukms.easy.test.mock.data;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import com.yukms.easy.test.exception.NoMockDataException;
import com.yukms.easy.test.mock.MockConfig;
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
    /**
     * 设置测试数据
     *
     * @param mockDataDirPath 测试数据路径
     */
    public static void setMockData(String mockDataDirPath) throws IOException {
        File mockDataDir = new File(mockDataDirPath);
        if (!mockDataDir.exists()) {
            // 不路径存在
            log.error("未找到测试数据");
            return;
        }
        File[] files = mockDataDir.listFiles((dir, name) -> name.endsWith(".yaml")
            // 过滤测试方法的数据
            && !name.startsWith(MockConfig.getTestClassAndMethod().getMethod().getName()));
        if (files == null) {
            // 测试文件不存在
            log.error("未找到测试数据");
            return;
        }
        Arrays.sort(files, File::compareTo);
        List<File> mockDataFields = new ArrayList<>(Arrays.asList(files));
        for (File file : mockDataFields) {
            log.info("添加测试数据 " + file.getName());
            MockData mockData = MockDataUtils.loadData(file);
            MockConfig.addFile(file);
            MockConfig.addData(mockData);
        }
    }

    /**
     * 是否有测试数据
     *
     * @return true表示有测试数据
     */
    public static boolean hasMockData() {
        return CollectionUtils.isNotEmpty(MockConfig.getData());
    }

    /**
     * 是否有测试数据
     *
     * @param method 请求方法
     * @param args   请求参数
     * @return true表示有测试数据
     */
    public static boolean needGetMockData(Method method, Object[] args) {
        LinkedList<MockData> data = MockConfig.getData();
        if (CollectionUtils.isEmpty(data)) {
            return false;
        }
        MockData mockData = data.peek();
        if (mockData.getMockClass() != method.getDeclaringClass()) {
            // 测试类不同
            return false;
        }
        if (!mockData.getMethodName().equals(method.getName())) {
            // 测试方法不同
            return false;
        }
        List<?> expectedArgs = mockData.getArgs();
        if (expectedArgs.size() != args.length) {
            // 测试参数个数不同
            return false;
        }
        for (int i = 0; i < expectedArgs.size(); i++) {
            Assert.assertEquals("参数不同 ", expectedArgs.get(i), args[i]);
        }
        return true;
    }

    /**
     * 获取mock数据
     *
     * @return mock数据
     */
    public static Object getMockData() {
        LinkedList<MockData> data = MockConfig.getData();
        if (CollectionUtils.isEmpty(data)) {
            throw new NoMockDataException();
        }
        // 文件也弹出
        MockConfig.getFiles().poll();
        return Objects.requireNonNull(data.poll()).getResult();
    }

    /**
     * 清除mock数据
     */
    public static void clearData() {
        LinkedList<File> files = MockConfig.getFiles();
        if (CollectionUtils.isEmpty(files)) {
            log.info("测试数据全部使用");
            return;
        }
        for (File file : files) {
            log.info(file.getName() + " 未被使用");
        }
    }

    private DataMocker() {}
}
