package com.dx.netty;

import com.dx.netty.protocol.NettyServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author duanxiang  duanxiang@haodelian.com
 * @version 1.0
 * @date 2022/3/22 下午10:32
 * @desc Java-Netty
 * 发布一个 spring 服务
 */
@ComponentScan(basePackages = {"com.dx.netty.spring","com.dx.netty.service"})
@SpringBootApplication
public class NettyRpcProvider {
    public static void main(String[] args) {
        //启动 spring 的容器,  没有依赖spring web
        SpringApplication.run(NettyRpcProvider.class,args);
        // 启动 netty server ，阻塞当前进程来监听请求
        new NettyServer("127.0.0.1",8081).startNettyServer();
    }
}
