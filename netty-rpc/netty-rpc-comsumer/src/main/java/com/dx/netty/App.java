package com.dx.netty;

/**
 * @desc
 * 动态代理
 *
 */
public class App {

    public static void main( String[] args ) {
        RpcClientProxy proxy = new RpcClientProxy();
        IUserService userService = proxy.clientProxy(IUserService.class,"127.0.0.1",8081);
        System.out.println(userService.saveUser("Hello World!") );
    }
}
