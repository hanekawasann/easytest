package com.yukms.easy.test.test.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.esotericsoftware.yamlbeans.YamlConfig;
import com.esotericsoftware.yamlbeans.YamlWriter;
import com.yukms.easy.test.test.mock.DataRecord;
import com.yukms.easy.test.test.mock.MockData;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.Test;
import org.springframework.util.ReflectionUtils;

/**
 * @author yukms 763803382@qq.com 2019/6/28 11:42
 */
@Log4j2
public final class DataRecordUtils {
    private static final ThreadLocal<DataRecord> RECORD_DATA = TransmittableThreadLocal.withInitial(DataRecord::new);
    private static final YamlConfig CONFIG = new YamlConfig();
    private static final String DATA_RECORDS_FILE_NAME = "dataRecords.yaml";
    private static final String FOLDER_NAME_SPLIT = "_";
    private static final String FILE_PATH_SPLIT = "\\";
    private static final String FILE_SUFFIX = ".yaml";
    private static final String CLASS_METHOD_SPLIT = "#";
    private static final String RECORD_FILE_PATH_PREFIX;

    static {
        YamlConfig.WriteConfig writeConfig = CONFIG.writeConfig;
        // 保存默认字段（BUG：无法保存null）
        //writeConfig.setWriteDefaultValues(true);
        // 文件字段顺序与字段定义顺序相同
        writeConfig.setKeepBeanPropertyOrder(true);
        // 格式化输出（BUG：解析可能报错）
        //writeConfig.setCanonical(true);
        // 不换行
        writeConfig.setWrapColumn(Integer.MAX_VALUE);
        // 中文不转义
        writeConfig.setEscapeUnicode(false);
        // 总是输出类名
        writeConfig.setWriteClassname(YamlConfig.WriteClassName.ALWAYS);

        File file = new File(".record");
        if (!file.exists()) {
            file.mkdirs();
        }
        RECORD_FILE_PATH_PREFIX = file.getAbsolutePath();
        log.info("记录根目录" + RECORD_FILE_PATH_PREFIX);
    }

    /**
     * 记录数据
     *
     * @param point  Spring AOP切点
     * @param result 方法方返回值
     */
    public static void recordData(ProceedingJoinPoint point, Object result) throws IOException {
        DataRecord dataRecord = RECORD_DATA.get();
        // 检查主目录
        String folderPath = dataRecord.getFolderPath();
        if (StringUtils.isBlank(folderPath)) {
            folderPath = initAndGetFolderPath(point);
        }
        Class<?> mockClazz = point.getTarget().getClass();
        Method method = AspectJUtils.getMethod(point);
        // 保存Mock文件
        String fileName = dataRecord.getNum().getAndIncrement() + FOLDER_NAME_SPLIT + mockClazz.getSimpleName() +
            CLASS_METHOD_SPLIT + method.getName() + FILE_SUFFIX;
        MockData data = new MockData();
        data.setMockClassName(mockClazz.getName());
        data.setMethodName(method.getName());
        data.setArgs(new ArrayList<>(Arrays.asList(point.getArgs())));
        if (result instanceof Throwable) {
            data.setExpectedClassName(result.getClass().getName());
        } else {
            data.setResult(result);
        }
        saveData(folderPath + fileName, data);
        // 记录文件
        dataRecord.addFileNames(fileName);
        log.info("保存记录文件" + fileName);
        saveSimpleData(folderPath + DATA_RECORDS_FILE_NAME, dataRecord.getFileNames());
    }

    /**
     * 获取Junit声明的测试方法
     *
     * @return Junit声明的测试方法
     * @throws NoSuchMethodException 没有找到测试的方法
     */
    public static StackTraceElement getTestStackTraceElement() throws NoSuchMethodException {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for (StackTraceElement element : stackTrace) {
            if (isTestStackTraceElement(element)) {
                return element;
            }
        }
        throw new NoSuchMethodException("未找到Junit测试方法");
    }

    private static boolean isTestStackTraceElement(StackTraceElement element) {
        try {
            Class<?> clazz = Class.forName(element.getClassName());
            Method method = ReflectionUtils.findMethod(clazz, element.getMethodName());
            if (method != null) {
                if (method.getAnnotation(Test.class) != null) {
                    return true;
                }
            }
        } catch (ClassNotFoundException ignored) { }
        return false;
    }

    private static void saveData(String path, Object obj) throws IOException {
        YamlWriter writer = new YamlWriter(new FileWriter(path), CONFIG);
        writer.write(obj);
        writer.close();
    }

    private static void saveSimpleData(String path, Object obj) throws IOException {
        YamlWriter writer = new YamlWriter(new FileWriter(path));
        writer.write(obj);
        writer.close();
    }

    private static String initAndGetFolderPath(ProceedingJoinPoint joinPoint) {
        String folderName = buildFolderName(joinPoint);
        DataRecord dataRecord = RECORD_DATA.get();
        String folderPath = RECORD_FILE_PATH_PREFIX + FILE_PATH_SPLIT + folderName + FILE_PATH_SPLIT;
        dataRecord.setFolderPath(folderPath);
        File file = new File(folderPath);
        if (!file.exists()) {
            file.mkdirs();
            log.info("创建记录文件夹" + folderName);
        }
        return folderPath;
    }

    private static String buildFolderName(ProceedingJoinPoint joinPoint) {
        return getFormatDateTime()//
            + FOLDER_NAME_SPLIT//
            + joinPoint.getTarget().getClass().getSimpleName()//
            + CLASS_METHOD_SPLIT//
            + AspectJUtils.getMethod(joinPoint).getName();
    }

    private static String getFormatDateTime() {
        return new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSSSSS").format(new Date());
    }

    private DataRecordUtils() { }
}
