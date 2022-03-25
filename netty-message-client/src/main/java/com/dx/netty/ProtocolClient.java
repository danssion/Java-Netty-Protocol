package com.dx.netty;

import com.dx.netty.codec.MessageRecordDecode;
import com.dx.netty.codec.MessageRecordEncode;
import com.dx.netty.opcode.OpCode;
import com.dx.netty.protocol.Header;
import com.dx.netty.protocol.MessageRecord;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.net.InetAddress;
import java.net.InetSocketAddress;

/**
 * @author duanxiang  duanxiang@haodelian.com
 * @version 1.0
 * @date 2022/3/17 下午8:44
 * @desc Java-Netty-Protocol
 */
public class ProtocolClient {
    public static void main(String[] args) {
        EventLoopGroup worker = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(worker).channel(NioSocketChannel.class)
            .handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(1024*1204,
                        9,4,0,0))
                    .addLast(new MessageRecordEncode())
                    .addLast(new MessageRecordDecode())
                    .addLast(new ClientHandler());
                }
            });
        try {
            ChannelFuture future = bootstrap.connect(new InetSocketAddress("localhost",8091)).sync();
            //客户端发起链接，发送数据
            Channel channel = future.channel();
            for (int i=0; i < 100 ; i++) {
                MessageRecord record = new MessageRecord();
                Header header = new Header();
                header.setSessionId(1000001);
                header.setReqType(OpCode.REQ.getCode());
                record.setHeader(header);
//                String body = "我是请求数据："+i;
                User u = new User();
                u.setName("name : "+1);
                record.setBody(u);
                channel.writeAndFlush(record);
            }
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            worker.shutdownGracefully();
        }
    }
}
