package com.dx.netty;

import com.dx.netty.protocol.MessageRecord;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author duanxiang  duanxiang@haodelian.com
 * @version 1.0
 * @date 2022/3/17 下午8:51
 * @desc Java-Netty-Protocol
 */
@Slf4j
public class ClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        MessageRecord record = (MessageRecord)msg;
        log.info("client Receive Message: "+record);
        super.channelRead(ctx, msg);
    }

}
