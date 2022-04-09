package com.dx.netty;

/**
 * @author duanxiang  duanxiang@haodelian.com
 * @version 1.0
 * @date 2022/4/8 22:49
 * @desc Java-Netty
 */
public interface IRegistryService {

    /**
     * 服务注册功能
     */
    void register(ServiceInfo serviceInfo) throws Exception;

    /**
     * 服务发现功能
     * @param serviceName
     */
    ServiceInfo discovery(String serviceName) throws Exception;
}
