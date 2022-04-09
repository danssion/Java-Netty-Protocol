package com.dx.netty.zookeeper;

import com.dx.netty.IRegistryService;
import com.dx.netty.ServiceInfo;
import com.dx.netty.loadbalance.ILoadBalance;
import com.dx.netty.loadbalance.RandomLoadBalance;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.details.JsonInstanceSerializer;

import java.util.Collection;
import java.util.List;

/**
 * @author duanxiang  duanxiang@haodelian.com
 * @version 1.0
 * @date 2022/4/8 22:59
 * @desc Java-Netty
 */
@Slf4j
public class ZookeeperRegistryService implements IRegistryService {

    //存在哪个根目录下, zookeeper 命名空间
    private static final String REGISTRY_PATH = "/registry";

    private ILoadBalance<ServiceInstance<ServiceInfo>> loadBalance;

    public ZookeeperRegistryService(String serviceAddress) throws Exception {
        CuratorFramework client = CuratorFrameworkFactory
            .newClient(serviceAddress,new ExponentialBackoffRetry(1000, 3));
        client.start();

        JsonInstanceSerializer<ServiceInfo> serializer = new JsonInstanceSerializer<>(ServiceInfo.class);
        this.serviceDiscovery = ServiceDiscoveryBuilder.builder(ServiceInfo.class)
            .client(client)
            .serializer(serializer)
            .basePath(REGISTRY_PATH)
            .build();
        //Corator 客户端初始化完成  启动
        this.serviceDiscovery.start();
        this.loadBalance = new RandomLoadBalance();
    }

    //curator 中提供的服务注册与发现的封装
    private final ServiceDiscovery<ServiceInfo> serviceDiscovery;

    @Override
    public void register(ServiceInfo serviceInfo) throws Exception {
        log.info("begin registry serviceInfo to Zookeeper ");
        //把服务端数据保存到 注册中心
        ServiceInstance<ServiceInfo> serviceInstance = ServiceInstance.<ServiceInfo>builder()
            .name(serviceInfo.getServiceName())
            .address(serviceInfo.getServiceAddress())
            .port(serviceInfo.getServicePort())
            .payload(serviceInfo)
            .build();
        this.serviceDiscovery.registerService(serviceInstance);
    }

    @Override
    public ServiceInfo discovery(String serviceName) throws Exception {
        log.info("begin discovery serviceInfo from Zookeeper ");
        Collection<ServiceInstance<ServiceInfo>> serviceInstances =
            this.serviceDiscovery.queryForInstances(serviceName);
        //得到一个列表  需要一个动态路由，由loadbalance 处理
        ServiceInstance<ServiceInfo> serviceInstance = this.loadBalance.select((List<ServiceInstance<ServiceInfo>>) serviceInstances);
        if(serviceInstance != null) {
            return serviceInstance.getPayload();
        }
        return null;
    }
}
