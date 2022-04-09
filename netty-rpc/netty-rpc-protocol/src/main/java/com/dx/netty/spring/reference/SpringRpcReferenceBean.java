package com.dx.netty.spring.reference;

import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.Proxy;

/**
 * @author duanxiang  duanxiang@haodelian.com
 * @version 1.0
 * @date 2022/4/5 18:55
 * @desc 消费端，需要注册一个动态代理（动态生成类的工厂bean） 需要FactoryBean
 *
 *  通过传入的 Object 及 接口类型 动态生成类
 *
 */
public class SpringRpcReferenceBean implements FactoryBean<Object> {

    private Object object;
    private String serverAddress;
    private int serverPort;
    //被代理对象的接口
    private Class<?> interfaceClass;

    @Override
    public Object getObject() throws Exception {
        return this.object;
    }

    public void init() {
        this.object =  Proxy.newProxyInstance(
            interfaceClass.getClassLoader(),
            new Class<?>[]{interfaceClass}, new RpcInvokerProxy(serverAddress,serverPort)  );
    }

    /**
     * 返回的注入类似是当前依赖注入  动态生成代理类的 接口名字
     * @return
     */
    @Override
    public Class<?> getObjectType() {
        return this.interfaceClass;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public void setInterfaceClass(Class<?> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }
}
