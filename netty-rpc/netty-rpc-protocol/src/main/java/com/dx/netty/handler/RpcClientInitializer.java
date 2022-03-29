package com.dx.netty.handler;

import com.dx.netty.codec.RpcDecoder;
import com.dx.netty.codec.RpcEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author duanxiang  duanxiang@haodelian.com
 * @version 1.0
 * @date 2022/3/28 21:58
 * @desc Java-Netty
 */
@Slf4j
public class RpcClientInitializer extends ChannelInitializer <SocketChannel> {
    @Override
    protected void initChannel(SocketChannel sc) throws Exception {
        log.info("begin RpcClientInitializer");
        sc.pipeline().addLast(
            new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 12,4,0,0))
            .addLast(new LoggingHandler())
            .addLast(new RpcEncoder())
            .addLast(new RpcDecoder())
            .addLast(new RpcClientHandler());
    }
}
