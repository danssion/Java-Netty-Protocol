package com.dx.netty.serial;

import java.io.IOException;

/**
 * @author duanxiang  duanxiang@haodelian.com
 * @version 1.0
 * @date 2022/3/23 下午9:39
 * @desc Java-Netty
 * 序列化接口
 */
public interface ISerializer  {
    /**
     * 序列化
     * @param obj
     * @param <T>
     * @return
     */
    <T> byte[] serialize(T obj) throws IOException;

    /**
     * 反序列化
     * @param data 数据
     * @param clazz 目标类型
     * @param <T>
     * @return
     */
    <T> T deserialize(byte[] data, Class<T> clazz);

    /**
     * 序列化的类型
     * @return
     */
    byte getType();
}
