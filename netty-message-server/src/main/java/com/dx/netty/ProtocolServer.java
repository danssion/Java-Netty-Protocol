package com.dx.netty;

import com.dx.netty.codec.MessageRecordDecode;
import com.dx.netty.codec.MessageRecordEncode;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lombok.extern.slf4j.Slf4j;


/**
 * @Projecet JavaExample-DdsignPattern  @Package com.dx.netty
 * @Copyright 2020 ~ 20**  @Author: dx
 * @Date: 2021/12/14 22:08
 * @Desc:
 */

@Slf4j
public class ProtocolServer {
    public static void main(String[] args) {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup work = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(boss,work)
            .channel(NioServerSocketChannel.class) // 可能是 工厂通过反射类构建
            .childHandler(new ChannelInitializer<SocketChannel>() {
                //针对客户端连接来设置 pipline

                @Override
                protected void initChannel(SocketChannel sh) throws Exception {
                    sh.pipeline()
                            .addLast(new LengthFieldBasedFrameDecoder(1024*1024, 9,
                                    4,0,0))
                            .addLast(new MessageRecordEncode())
                            .addLast(new MessageRecordDecode())
                            .addLast(new ServerHandler());
                }
            });
        try {
            //CompletableFuture netty 没有使用，而是基于Future 自己实现相关的回调
            ChannelFuture  channelFuture = bootstrap.bind(8091).sync();
            log.info("Protocol Server start success {8091}");
            //ChannelFuture 拿到channel 已经是服务端初始完成的 socket
            // 关闭事件同步等待
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            work.shutdownGracefully();
            boss.shutdownGracefully();
        }
    }
}
