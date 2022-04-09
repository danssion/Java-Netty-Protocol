package com.dx.netty;

/**
 * @author duanxiang  duanxiang@haodelian.com
 * @version 1.0
 * @date 2022/4/9 16:05
 * @desc Java-Netty
 */
public enum RegistryType {
    ZOOKEEPER((byte)0),
    EUREKA((byte)1);

    private byte code;

    RegistryType(byte code) {
        this.code = code;
    }

    public static RegistryType findByCode(int code) {
        for(RegistryType reqType : RegistryType.values()) {
            if(code == reqType.code){
                return reqType;
            }
        }
        return null;
    }
}
