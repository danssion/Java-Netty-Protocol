package com.dx.netty;

import java.lang.reflect.Proxy;

/**
 * @author duanxiang  duanxiang@haodelian.com
 * @version 1.0
 * @date 2022/3/25 下午10:37
 * @desc Java-Netty
 *  改为Spring 方式启动，可以不用
 */
@Deprecated
public class RpcClientProxy {

    public <T> T clientProxy(final Class<T> interfaceCls, final String host, int port) {
        return (T) Proxy.newProxyInstance(
            interfaceCls.getClassLoader(),
            new Class<?>[]{interfaceCls}, new RpcInvokerProxy(host,port)  );
    }
}
