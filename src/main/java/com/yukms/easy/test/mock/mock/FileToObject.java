package com.yukms.easy.test.mock.mock;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.lang.reflect.Type;

/**
 * 文件转换为对象的方式
 *
 * @author yukms 763803382@qq.com 2019/3/26.
 */
public enum FileToObject {
    /** 部分接口没有返回值 */
    VOID {
        @Override
        public Object toObject(InputStream inputStream, Type returnType) {
            return null;
        }
    },
    JSON {
        @Override
        public Object toObject(InputStream inputStream, Type returnType) {
            try {
                return com.alibaba.fastjson.JSON.parseObject(getFileString(inputStream), returnType);
            } catch (IOException e) {
                return null;
            }
        }
    },
    READ_OBJECT {
        @Override
        public Object toObject(InputStream inputStream, Type returnType) {
            try (ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
                return objectInputStream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                return null;
            }
        }
    };

    /**
     * 将输入流转换为所需的类
     *
     * @param inputStream 输入流
     * @param returnType  所需的类型
     * @return 结果
     */
    public abstract Object toObject(InputStream inputStream, Type returnType);

    private static String getFileString(InputStream inputStream) throws IOException {
        StringBuilder builder = new StringBuilder(64);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        return builder.toString();
    }
}
