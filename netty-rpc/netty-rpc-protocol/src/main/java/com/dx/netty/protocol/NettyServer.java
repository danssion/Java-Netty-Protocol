package com.dx.netty.protocol;

import com.dx.netty.handler.RpcServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author duanxiang  duanxiang@haodelian.com
 * @version 1.0
 * @date 2022/3/24 下午10:22
 * @desc Java-Netty
 */
@Slf4j
public class NettyServer {
    //服务地址
    private String serverAddress;
    //端口
    private int serverPort;

    public NettyServer(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    public void startNettyServer() {
        log.info("===== begin start netty server =====");
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(boss,worker)
            .channel(NioServerSocketChannel.class)
            .childHandler(new RpcServerInitializer());
        try {
            ChannelFuture future = bootstrap.bind(this.serverAddress,this.serverPort).sync();
            log.info("server started success on port : {} ~~!",this.serverPort);
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            worker.shutdownGracefully();
            boss.shutdownGracefully();
        }
    }
}
