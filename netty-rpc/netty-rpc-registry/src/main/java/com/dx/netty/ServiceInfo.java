package com.dx.netty;

import lombok.Data;

/**
 * @author duanxiang  duanxiang@haodelian.com
 * @version 1.0
 * @date 2022/4/8 22:51
 * @desc Java-Netty  用来保存服务信息
 */
@Data
public class ServiceInfo {
    private String serviceName;
    private String serviceAddress;
    private int servicePort;
}
