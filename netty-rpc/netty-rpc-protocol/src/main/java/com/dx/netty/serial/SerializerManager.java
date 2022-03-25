package com.dx.netty.serial;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author duanxiang  duanxiang@haodelian.com
 * @version 1.0
 * @date 2022/3/23 下午9:59
 * @desc Java-Netty
 * 定义一个 工厂类
 */
public class SerializerManager {
    private final static ConcurrentHashMap<Byte, ISerializer> serializer = new ConcurrentHashMap<>();

    static {
        ISerializer json = new JsonSerializer();
        ISerializer java = new JavaSerializer();
        serializer.put(json.getType(),json);
        serializer.put(java.getType(),java);
    }

    public static ISerializer getSerializer(byte key) {
        ISerializer iser = serializer.get(key);
        if(iser == null) {
            return new JavaSerializer();
        }
        return iser;
    }
}
