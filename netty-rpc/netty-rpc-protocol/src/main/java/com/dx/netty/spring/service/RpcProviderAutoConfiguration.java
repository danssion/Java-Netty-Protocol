package com.dx.netty.spring.service;

import com.dx.netty.IRegistryService;
import com.dx.netty.RegistryFactory;
import com.dx.netty.RegistryType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.UnknownHostException;

/**
 * @author duanxiang  duanxiang@haodelian.com
 * @version 1.0
 * @date 2022/4/4 21:56
 * @desc Java-Netty 将配置类自动装载到 bean 中
 */
@Configuration
@EnableConfigurationProperties(RpcServerProperties.class)
public class RpcProviderAutoConfiguration {

    @Bean
    public SpringRpcProviderBean springRpcProviderBean(RpcServerProperties rpcServerProperties) throws UnknownHostException {
        IRegistryService registryService = RegistryFactory.createRegistryService(rpcServerProperties.getRegistryAddress(),
            RegistryType.findByCode(rpcServerProperties.getRegistryType()));
//        return new SpringRpcProviderBean(rpcServerProperties.getServerPort());
        return new SpringRpcProviderBean(rpcServerProperties.getServerPort(), registryService);
    }
}
