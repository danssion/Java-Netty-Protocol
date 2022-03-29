package com.dx.netty.handler;

import com.dx.netty.core.RequestHolder;
import com.dx.netty.core.RpcFuture;
import com.dx.netty.core.RpcProtocol;
import com.dx.netty.core.RpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author duanxiang  duanxiang@haodelian.com
 * @version 1.0
 * @date 2022/3/29 21:42
 * @desc Java-Netty
 */
@Slf4j
public class RpcClientHandler extends SimpleChannelInboundHandler<RpcProtocol<RpcResponse>> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcProtocol<RpcResponse> msg) throws Exception {
        log.info("Receive Rpc Server Result");
        long requestId = msg.getHeader().getRequestId();
        RpcFuture<RpcResponse> future = RequestHolder.REQUEST_MAP.remove(requestId);
        // 返回结果
        future.getPromise().setSuccess(msg.getContent());
    }
}
