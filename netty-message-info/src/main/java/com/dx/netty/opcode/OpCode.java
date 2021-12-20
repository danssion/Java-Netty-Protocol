package com.dx.netty.opcode;

/**
 * @Projecet JavaExample-DdsignPattern
 * @Package com.dx.netty.opcode
 * @Desc: 枚举类，消息类型
 *
 * @Copyright 2020 ~ 20**
 * @Author: dx
 * @Date: 2021/12/10 23:02
 * @Version V1.0
 */
public enum  OpCode {

    REQ((byte)0),
    RES((byte)1),
    PING((byte)2),
    PONG((byte)3);

    private byte code;

    OpCode(byte code) {
        this.code = code;
    }

    public byte getCode() {
        return code;
    }
}
