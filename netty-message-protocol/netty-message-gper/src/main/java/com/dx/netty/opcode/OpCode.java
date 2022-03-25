package com.dx.netty.opcode;

import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;

/**
 * @author duanxiang  duanxiang@haodelian.com
 * @version 1.0
 * @date 2022/3/14 下午10:48
 * @desc Java-Netty-Protocol
 */
public enum OpCode {

    REQ((byte)0),
    RES((byte)1),
    PING((byte)2),
    PONG((byte)3);

    private byte code;

    OpCode(byte opcode) {
        code = opcode;
    }

    public byte code() {
        return this.code;
    }
}
