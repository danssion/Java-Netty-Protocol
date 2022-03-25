package com.dx.netty;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author duanxiang  duanxiang@haodelian.com
 * @version 1.0
 * @date 2022/3/25 下午10:41
 * @desc Java-Netty
 */
@Slf4j
public class RpcInvokerProxy implements InvocationHandler {
    private String host;
    private int port;

    public RpcInvokerProxy(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("被代理执行");

        return "Dx";
    }
}
