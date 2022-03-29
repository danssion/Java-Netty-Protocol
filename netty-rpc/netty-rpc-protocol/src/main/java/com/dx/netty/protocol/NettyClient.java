package com.dx.netty.protocol;

import com.dx.netty.core.RpcProtocol;
import com.dx.netty.core.RpcRequest;
import com.dx.netty.handler.RpcClientInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author duanxiang  duanxiang@haodelian.com
 * @version 1.0
 * @date 2022/3/28 21:53
 * @desc Java-Netty
 */
@Slf4j
public class NettyClient {
    private final Bootstrap bootstrap;
    private final EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
    private String serviceAddress;
    private int servicePort;


    public NettyClient(String serviceAddress, int servicePort) {
        log.info("begin init Netty Client {} : {}",serviceAddress ,servicePort);
        bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup)
            .channel(NioSocketChannel.class)
            .handler(new RpcClientInitializer());
        this.serviceAddress = serviceAddress;
        this.servicePort = servicePort;
    }

    public void sendRequest(RpcProtocol<RpcRequest> protocol) throws InterruptedException {
        final ChannelFuture future = bootstrap.connect(this.serviceAddress,this.servicePort).sync();
        future.addListener(listener ->{
            if (future.isSuccess()) {
                log.info("connect rpc server {} success ",this.serviceAddress);
            } else {
                log.error("connect rpc server {} fail!",this.serviceAddress);
                future.cause().printStackTrace();
                eventLoopGroup.shutdownGracefully();
            }
        });
        log.info("begin transfer ~~~~~!");
        // 返回一个 future
        future.channel().writeAndFlush(protocol);
    }
}
