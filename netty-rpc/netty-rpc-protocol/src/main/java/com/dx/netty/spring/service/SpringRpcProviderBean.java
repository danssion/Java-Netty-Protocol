package com.dx.netty.spring.service;

import com.dx.netty.IRegistryService;
import com.dx.netty.ServiceInfo;
import com.dx.netty.annotation.DxRemoteService;
import com.dx.netty.protocol.NettyServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author duanxiang  duanxiang@haodelian.com
 * @version 1.0
 * @date 2022/4/3 21:44
 * @desc Java-Netty
 *  BeanPostProcessor  在bean 实例化之前、之后会触发回调,不需要通过SPI方式扩展
 *  InitializingBean  触发一个 afterPropertiesSet
 */

@Slf4j
public class SpringRpcProviderBean implements InitializingBean, BeanPostProcessor {
    private final int port;
    private final String address;

    // 服务注册
    private final IRegistryService registryService;

    public SpringRpcProviderBean(int port,IRegistryService registryService) throws UnknownHostException {
        this.port = port;
        InetAddress  adds = InetAddress.getLocalHost();
        this.address = adds.getHostAddress();
        this.registryService = registryService;
    }

    public SpringRpcProviderBean(int port) throws UnknownHostException {
        this.port = port;
        InetAddress  adds = InetAddress.getLocalHost();
        this.address = adds.getHostAddress();
        this.registryService = null;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("begin deploy Netty Server to host {} on port {}",this.address,this.port);
        //只启动一下，用异步线程的方式启动， 不会阻塞影响启动过程的
        new Thread(() -> {
            new NettyServer(this.address, this.port).startNettyServer();
        }).start();
    }

    /**
     * @desc 前一讲中  客户端通过代理实现请求到 Server 端，Server 通过RpcServerHandler 进行处理，
     *          通过SpringBeanManager 调用不同流程
     *       现在通过这里实现只有指定注解的类才可以发布
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        //只要bean声明了DxRemoteService 注解，则需要把该服务发布到网络（云效被调用）
        if(bean.getClass().isAnnotationPresent(DxRemoteService.class)) {
            //全路径
            String serviceName = bean.getClass().getInterfaces()[0].getName();
            //获取当前所有bean 的方法
            Method[] methods = bean.getClass().getDeclaredMethods();
            for(Method method: methods) {
                String key = serviceName+"."+method.getName();
                BeanMethod beanMethod = new BeanMethod();
                beanMethod.setBean(bean);
                beanMethod.setMethod(method);
                Mediator.beanMethodMap.put(key,beanMethod);

                //构建数据 发布服务
                ServiceInfo serviceInfo = new ServiceInfo();
                serviceInfo.setServiceName(serviceName);
                serviceInfo.setServiceAddress(this.address);
                serviceInfo.setServicePort(this.port);

                try {
                    registryService.register(serviceInfo);
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("register service {} fail",serviceName,e);
                }
            }
        }
        return bean;
    }
}
