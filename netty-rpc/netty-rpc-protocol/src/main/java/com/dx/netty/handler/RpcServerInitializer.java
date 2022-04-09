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
    protected void initChannel(SocketChannel ch) {
        //头信息 长度与下面设置的不符合，不能被 decode
        ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,
            12,4,0,0))
            .addLast(new RpcDecoder())
            .addLast(new RpcEncoder())
//            .addLast(new RpcServerHandler());
            .addLast(new RpcServerBeanHandler());
    }
}
