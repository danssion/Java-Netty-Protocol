package com.dx.netty.handler;

import com.dx.netty.codec.RpcDecoder;
import com.dx.netty.codec.RpcEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @author duanxiang  duanxiang@haodelian.com
 * @version 1.0
 * @date 2022/3/25 下午9:38
 * @desc Java-Netty
 */
public class RpcServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,
            12,4,0,0))
            .addLast(new RpcServerHandler())
            .addLast(new RpcDecoder());
//            .addLast(new RpcEncoder());

    }
}
