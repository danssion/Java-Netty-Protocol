package com.dx.netty.spring.reference;

import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @author duanxiang  duanxiang@haodelian.com
 * @version 1.0
 * @date 2022/4/7 21:24
 * @desc Java-Netty
 *  可以得到一个 Environment 对象
 *  EnableConfigurationProperties  测试无法注入，原因待查
 */
@Configuration
public class RpcReferenceAutoConfiguration implements EnvironmentAware {
    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public SpringRpcReferencePostProcessor postProcessor(){
        RpcClientProperties rc = new RpcClientProperties();
        rc.setServiceAddress(this.environment.getProperty("dx.client.serviceAddress"));
        int port = Integer.parseInt(this.environment.getProperty("dx.client.servicePort"));
        rc.setServicePort(port);
        return new SpringRpcReferencePostProcessor(rc);
    }
}
