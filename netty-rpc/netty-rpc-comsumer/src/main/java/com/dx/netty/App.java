package com.dx.netty;

/**
 * Hello world!
 *
 */
public class App {
    
    public static void main( String[] args )
    {
        RpcClientProxy proxy = new RpcClientProxy();
        IUserService userService = proxy.clientProxy(IUserService.class,"localhost",8081);
        System.out.println( userService.saveUser("Hello World!") );
    }
}
