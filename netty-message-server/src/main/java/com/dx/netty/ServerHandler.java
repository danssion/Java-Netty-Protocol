package com.dx.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @Projecet JavaExample-DdsignPattern  @Package com.dx.netty
 * @Copyright 2020 ~ 20**  @Author: dx
 * @Date: 2021/12/14 22:30
 * @Desc:
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
    }
}
