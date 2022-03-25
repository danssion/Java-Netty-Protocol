package com.dx.netty;

import lombok.Data;

import java.io.Serializable;

/**
 * @author duanxiang  duanxiang@haodelian.com
 * @version 1.0
 * @date 2022/3/20 下午4:06
 * @desc Java-Netty-Protocol
 *
 * 不实现 Serializable 报错
 * java.io.NotSerializableException: com.dx.netty.User
 * 	at java.io.ObjectOutputStream.writeObject0(ObjectOutputStream.java:1184)
 * 	at java.io.ObjectOutputStream.writeObject(ObjectOutputStream.java:348)
 */
@Data
public class User implements Serializable {
    /**
     * 所有序列化类都会有，没有指定会默认生成一个
     * 用于校验对象是否发生了变化
     */
    private static final long serialVersionUID = 6180612777660299622L;
    private String name;
}
