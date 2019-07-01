package com.yukms.easy.test.test.mock;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import com.yukms.easy.test.test.util.ClassUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.util.ReflectionUtils;

/**
 * 数据模拟器
 *
 * @author yukms 763803382@qq.com 2019/3/26.
 */
@Log4j2
public final class DataMocker {
    private static final ThreadLocal<MockDatas> THREAD_LOCAL = ThreadLocal.withInitial(MockDatas::new);

    private DataMocker() {}

    /**
     * 设置mock数据
     *
     * @param fileName        mock文件名
     * @param inputStream     文件对应的输入流
     * @param fileToObject    文件转换为对象的方式
     * @param requestAsserter 请求断言器
     */
    public static void setResponseMockData(String fileName, InputStream inputStream, FileToObject fileToObject,
        Object requestAsserter) {
        Objects.requireNonNull(fileToObject, "文件转换方式不能为空");
        Objects.requireNonNull(requestAsserter, "请求断言器不能为空");
        MockData mockData = new MockData();
        mockData.setFileName(fileName);
        mockData.setInputStream(inputStream);
        mockData.setFileToObject(fileToObject);
        mockData.setMock(requestAsserter);
        MockDatas mockDatas = THREAD_LOCAL.get();
        mockDatas.setMockData(mockData);
        log.info("设置Mock数据：" + fileName);
    }

    /**
     * 是否获取Mock数据（通过断言器覆盖的方法与请求的方法做比较）
     *
     * @param requestMethod 请求方法
     * @return true表示获取Mock数据
     */
    public static boolean isGetMockData(Method requestMethod) {
        MockData mockData = THREAD_LOCAL.get().peek();
        return null != mockData && ClassUtil.isOverrideMethod(mockData.getMock().getClass(), requestMethod);
    }

    /**
     * 获取mock数据
     *
     * @param requestMethod 请求方法
     * @param args          请求参数
     * @return mock数据
     */
    public static Object getData(Method requestMethod, Object[] args) {
        MockData mockData = THREAD_LOCAL.get().poll();
        Object mock = mockData.getMock();
        // 执行mock中参数校验
        Method assertMockMethod = ReflectionUtils
            .findMethod(mock.getClass(), requestMethod.getName(), requestMethod.getParameterTypes());
        Objects.requireNonNull(assertMockMethod).setAccessible(true);
        ReflectionUtils.invokeMethod(assertMockMethod, mock, args);
        return buildData(mockData, assertMockMethod.getGenericReturnType());
    }

    /**
     * 清除mock数据
     */
    public static void clearData() {
        MockDatas mockDatas = THREAD_LOCAL.get();
        Iterator<MockData> iterator = mockDatas.getMockDataList().iterator();
        while (iterator.hasNext()) {
            MockData mockData = iterator.next();
            try {
                InputStream stream = mockData.getInputStream();
                if (stream != null) {
                    stream.close();
                }
            } catch (IOException e) {
                log.info("释放输入流失败：" + mockData.toString());
            }
            log.info("清除Mock数据：" + mockData.toString());
            if (!mockData.isUsed()) {
                log.error("Mock数据未被使用：" + mockData.toString());
            }
            iterator.remove();

        }
        mockDatas.getIndex().set(0);
    }

    /**
     * 构建Mock数据
     *
     * @param mockData   mock数据
     * @param returnType 返回类型
     * @return 结果
     */
    private static Object buildData(MockData mockData, Type returnType) {
        Object result = null;
        try (InputStream inputStream = mockData.getInputStream()) {
            result = mockData.getFileToObject().toObject(inputStream, returnType);
        } catch (IOException e) {
            log.error("Mock数据文件输入流关闭失败");
        }
        log.info("获取Mock数据成功：" + mockData.getFileName());
        return result;
    }

    /**
     * mock数据集合
     */
    private static class MockDatas {
        private AtomicInteger index = new AtomicInteger();
        private List<MockData> mockDataList = new ArrayList<>();

        MockData peek() {
            int i = index.get();
            if (i >= mockDataList.size()) {
                return null;
            }
            return mockDataList.get(i);
        }

        MockData poll() {
            checkIndexOutOfMockData();
            MockData mockData = mockDataList.get(index.getAndIncrement());
            mockData.setUsed(true);
            return mockData;
        }

        void checkIndexOutOfMockData() {
            int i = index.get();
            if (i >= mockDataList.size()) {
                throw new IndexOutOfBoundsException(
                    "当前获取Mock数据第" + (i + 1) + "次，实际上仅mock了" + mockDataList.size() + "条数据");
            }
        }

        void setMockData(MockData mockData) {
            this.mockDataList.add(mockData);
        }

        List<MockData> getMockDataList() {
            return mockDataList;
        }

        AtomicInteger getIndex() {
            return index;
        }

        public void setIndex(AtomicInteger index) {
            this.index = index;
        }

        void setMockDataList(List<MockData> mockDataList) {
            this.mockDataList = mockDataList;
        }
    }

    private static class MockData {
        /** 文件名 */
        private String fileName;
        /** 输入流 */
        private InputStream inputStream;
        /** 文件转换为对线的方式 */
        private FileToObject fileToObject;
        /** 需要mock的对象 */
        private Object mock;
        /** 已经被使用 */
        private boolean used;

        String getFileName() {
            return fileName;
        }

        void setFileName(String fileName) {
            this.fileName = fileName;
        }

        InputStream getInputStream() {
            return inputStream;
        }

        void setInputStream(InputStream inputStream) {
            this.inputStream = inputStream;
        }

        FileToObject getFileToObject() {
            return fileToObject;
        }

        void setFileToObject(FileToObject fileToObject) {
            this.fileToObject = fileToObject;
        }

        Object getMock() {
            return mock;
        }

        void setMock(Object mock) {
            this.mock = mock;
        }

        private boolean isUsed() {
            return used;
        }

        private void setUsed(boolean used) {
            this.used = used;
        }

        @Override
        public String toString() {
            return "MockData{" + "fileName='" + fileName + '\'' + ", fileToObject=" + fileToObject + ", used=" + used +
                '}';
        }
    }
}
