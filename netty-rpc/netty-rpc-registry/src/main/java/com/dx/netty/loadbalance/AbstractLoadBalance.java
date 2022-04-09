package com.dx.netty.loadbalance;

import com.dx.netty.ServiceInfo;
import org.apache.curator.x.discovery.ServiceInstance;

import java.util.List;

/**
 * @author duanxiang  duanxiang@haodelian.com
 * @version 1.0
 * @date 2022/4/9 15:35
 * @desc Java-Netty  抽象类，定义一些模板方法
 */
public abstract class AbstractLoadBalance implements ILoadBalance<ServiceInstance<ServiceInfo>> {
    @Override
    public ServiceInstance<ServiceInfo> select(List<ServiceInstance<ServiceInfo>> servers) {
       if (servers == null || servers.size() == 0) {
           return null;
       }
       if(servers.size() == 1) {
           return servers.get(0);
       }
       return doSelect(servers);
    }

    /**
     * 具体的实现
     * @param servers
     * @return
     */
    protected abstract ServiceInstance<ServiceInfo> doSelect(List<ServiceInstance<ServiceInfo>> servers);
}
