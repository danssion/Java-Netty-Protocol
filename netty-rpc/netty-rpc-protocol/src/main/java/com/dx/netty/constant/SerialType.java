package com.dx.netty.constant;

/**
 * @author duanxiang  duanxiang@haodelian.com
 * @version 1.0
 * @date 2022/3/23 下午9:33
 * @desc Java-Netty
 */
public enum SerialType {

    JSON_SERIAL((byte)1),
    JAVA_SERIAL((byte)2);

    private byte code;

    SerialType(byte code){
        this.code = code;
    }

    public byte getCode() {
        return code;
    }

    public byte code() {
        return this.code;
    }
}
