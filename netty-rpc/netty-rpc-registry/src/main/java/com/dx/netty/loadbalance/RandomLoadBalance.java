package com.dx.netty.loadbalance;

import com.dx.netty.ServiceInfo;
import org.apache.curator.x.discovery.ServiceInstance;

import java.util.List;
import java.util.Random;

/**
 * @author duanxiang  duanxiang@haodelian.com
 * @version 1.0
 * @date 2022/4/9 15:49
 * @desc Java-Netty
 */
public class RandomLoadBalance extends AbstractLoadBalance{
    @Override
    protected ServiceInstance<ServiceInfo> doSelect(List<ServiceInstance<ServiceInfo>> servers) {
        int len = servers.size();
        Random random = new Random();
        return servers.get(random.nextInt(len));
    }
}
