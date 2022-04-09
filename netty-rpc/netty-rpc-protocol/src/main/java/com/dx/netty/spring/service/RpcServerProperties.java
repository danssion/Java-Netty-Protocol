package com.dx.netty.spring.service;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author duanxiang  duanxiang@haodelian.com
 * @version 1.0
 * @date 2022/4/4 21:50
 * @desc Java-Netty 配置类
 */
@Data
@ConfigurationProperties(prefix = "dx.rpc")
public class RpcServerProperties {

//    private String serverAddress;
    private int serverPort;

    //注册中心地址
    private String registryAddress;
    //注册中心类型
    private byte registryType;
}
