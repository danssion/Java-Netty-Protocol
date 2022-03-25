package com.dx.netty.constant;

/**
 * @author duanxiang  duanxiang@haodelian.com
 * @version 1.0
 * @date 2022/3/23 下午9:30
 * @desc Java-Netty
 */
public enum ReqType {
    REQUEST((byte)1),
    RESPONSE((byte)2),
    HEARTBEAT((byte)3);

    private byte code;

    ReqType(byte code){
        this.code = code;
    }

    public byte getCode() {
        return code;
    }

    public byte code() {
        return this.code;
    }

    public static ReqType findByCode(int code) {
        for(ReqType reqType : ReqType.values()) {
            if(code == reqType.code){
                return reqType;
            }
        }
        return null;
    }
}
