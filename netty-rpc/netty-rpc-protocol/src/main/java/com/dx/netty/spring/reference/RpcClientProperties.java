package com.dx.netty.spring.reference;

import lombok.Data;

/**
 * @author duanxiang  duanxiang@haodelian.com
 * @version 1.0
 * @date 2022/4/7 21:08
 * @desc Java-Netty
 */

@Data
public class RpcClientProperties {
    private String serviceAddress = "127.0.0.1";
    private int servicePort = 20880;
}
