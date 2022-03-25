package com.dx.netty;

import com.dx.netty.opcode.OpCode;
import com.dx.netty.protocol.MessageRecord;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @Projecet JavaExample-DdsignPattern  @Package com.dx.netty
 * @Copyright 2020 ~ 20**  @Author: dx
 * @Date: 2021/12/14 22:30
 * @Desc:
 */
@Slf4j
public class ServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //当前 ServerHandler 是 SocketChannel 触发的
        MessageRecord record = (MessageRecord)msg;
        log.info("Server Receive Msg:{} ",record);
        record.setBody("Server Response Message");
        record.getHeader().setReqType(OpCode.RES.getCode());
        ctx.writeAndFlush(record);
//        super.channelRead(ctx, msg);
    }
}
