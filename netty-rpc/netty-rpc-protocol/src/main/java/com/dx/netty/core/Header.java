package com.dx.netty.core;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author duanxiang  duanxiang@haodelian.com
 * @version 1.0
 * @date 2022/3/23 下午9:14
 * @desc Java-Netty
 */
@AllArgsConstructor
@Data
public class Header implements Serializable {

    //魔数 2 字节  ， 验证报文的身份类型
    private short magic;

    //序列化列席  1字节
    private byte serialType;
    // 消息类型 1 字节
    private byte reqType;
    // 请求id 8 字节
    private long requestId;

    // 报文长度 4 字节
    private int length;

}
