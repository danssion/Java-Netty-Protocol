package com.dx.netty;

/**
 * @desc
 * 动态代理
 * 非Springbean 方式
 */
@Deprecated
public class App {

    public static void main( String[] args ) {
        RpcClientProxy proxy = new RpcClientProxy();
        IUserService userService = proxy.clientProxy(IUserService.class,"127.0.0.1",20880);
        System.out.println(userService.saveUser("Hello World!") );
    }
}
