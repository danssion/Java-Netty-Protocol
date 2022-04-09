package com.dx.netty;

import com.dx.netty.zookeeper.ZookeeperRegistryService;

/**
 * @author duanxiang  duanxiang@haodelian.com
 * @version 1.0
 * @date 2022/4/9 16:08
 * @desc Java-Netty
 */
public class RegistryFactory {

    public static IRegistryService createRegistryService(String address, RegistryType registryType) {
        IRegistryService registryService = null;
        try{
            switch (registryType){
                case EUREKA:
                    //TODO
                    break;
                case ZOOKEEPER:
                    registryService = new ZookeeperRegistryService(address);
                    break;
                default:
                    registryService = new ZookeeperRegistryService(address);
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return registryService;
    }
}
