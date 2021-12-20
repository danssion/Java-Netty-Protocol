package com.dx.netty.protocol;

import lombok.Data;

/**
 * @BelongProjecet JavaExample-DdsignPattern
 * @BelongPackage com.dx.netty.protocol
 * @desc 消息头
 * 13 个字节
 *
 * @Copyright 2020 ~ 20**
 * @Author: dx
 * @Date: 2021/12/10 22:58
 * @Version V1.0
 */
@Data
public class Header {
    private long sessionId; // 会话id  8 个字节
    private byte reqType;   // 消息类型  1 个字节
    private int length;  //消息体的长度  4 个字节
}
