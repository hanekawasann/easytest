package com.yukms.easy.test.mock;

import java.io.File;
import java.util.LinkedList;

import com.yukms.easy.test.mock.annotation.EasyTestMock;
import com.yukms.easy.test.mock.data.MockData;
import com.yukms.easy.test.mock.junit.TestClassAndMethod;

/**
 * @author hudingpeng hudingpeng@souche.com 2019/7/16 17:09
 */
public class MockConfig {
    private static final ThreadLocal<MockConfig> THREAD_LOCAL = ThreadLocal.withInitial(MockConfig::new);
    private boolean saveData;
    private LinkedList<File> files = new LinkedList<>();
    private LinkedList<MockData> data = new LinkedList<>();
    private TestClassAndMethod testClassAndMethod;

    public static TestClassAndMethod getTestClassAndMethod() {
        return THREAD_LOCAL.get().testClassAndMethod;
    }

    public static void setTestClassAndMethod(TestClassAndMethod testClassAndMethod) {
        THREAD_LOCAL.get().testClassAndMethod = testClassAndMethod;
        EasyTestMock easyTestMock = testClassAndMethod.getClazz().getAnnotation(EasyTestMock.class);
        if (easyTestMock != null) {
            MockConfig.setSaveData(easyTestMock.isSaveData());
            return;
        }
        easyTestMock = testClassAndMethod.getDescription().getAnnotation(EasyTestMock.class);
        if (easyTestMock != null) {
            MockConfig.setSaveData(easyTestMock.isSaveData());
        }
    }

    public static boolean isSaveData() {
        return THREAD_LOCAL.get().saveData;
    }

    public static void setSaveData(boolean saveData) {
        MockConfig mockConfig = THREAD_LOCAL.get();
        mockConfig.saveData = saveData;
    }

    public static void addFile(File file) {
        MockConfig mockConfig = THREAD_LOCAL.get();
        mockConfig.files.add(file);
    }

    public static LinkedList<File> getFiles() {
        return THREAD_LOCAL.get().files;
    }

    public static void addData(MockData mockData) {
        MockConfig mockConfig = THREAD_LOCAL.get();
        mockConfig.data.add(mockData);
    }

    public static LinkedList<MockData> getData() {
        return THREAD_LOCAL.get().data;
    }
}
