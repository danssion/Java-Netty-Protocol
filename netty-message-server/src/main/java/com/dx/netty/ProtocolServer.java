package com.dx.netty;

import com.dx.netty.codec.MessageRecordDecode;
import com.dx.netty.codec.MessageRecordEncode;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @Projecet JavaExample-DdsignPattern  @Package com.dx.netty
 * @Copyright 2020 ~ 20**  @Author: dx
 * @Date: 2021/12/14 22:08
 * @Desc:
 */
public class ProtocolServer {
    public static void main(String[] args) {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup work = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(boss,work)
                .channel(NioServerSocketChannel.class) // 可能是 工厂通过反射类构建
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    //针对客户端连接来这只 pipline
                    @Override
                    protected void initChannel(SocketChannel sh) throws Exception {
                        sh.pipeline()
                                .addLast(new LengthFieldBasedFrameDecoder(1024*1024, 9,
                                        4,0,0))
                                .addLast(new MessageRecordEncode())
                                .addLast(new MessageRecordDecode())


                    }
                });
    }
}
