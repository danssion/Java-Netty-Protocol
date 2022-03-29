package com.dx.netty;

import com.dx.netty.constant.ReqType;
import com.dx.netty.constant.RpcContent;
import com.dx.netty.constant.SerialType;
import com.dx.netty.core.*;
import com.dx.netty.protocol.NettyClient;
import io.netty.channel.DefaultEventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.util.concurrent.DefaultPromise;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author duanxiang  duanxiang@haodelian.com
 * @version 1.0
 * @date 2022/3/25 下午10:41
 * @desc Java-Netty
 */
@Slf4j
public class RpcInvokerProxy implements InvocationHandler {
    private String host;
    private int port;

    public RpcInvokerProxy(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//        System.out.println("被代理执行");
        log.info("begin invoke target server");
        RpcProtocol<RpcRequest> reqProtocol = new RpcProtocol<>();
        long requestId = RequestHolder.REQUEST_ID.incrementAndGet();
        // length 在 编码器中设置
        Header header = new Header(RpcContent.MAGIC, SerialType.JSON_SERIAL.code(),
            ReqType.REQUEST.code(), requestId,0);
        reqProtocol.setHeader(header);
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setClassName(method.getDeclaringClass().getName());
        rpcRequest.setMethodName(method.getName());
        rpcRequest.setParamsTypes(method.getParameterTypes());
        rpcRequest.setParams(args);

        reqProtocol.setContent(rpcRequest);

        NettyClient nettyClient = new NettyClient(host,port);
        // 传入 Eventloop 会在 Promise 中轮询 获取到结果设置到 future中
        RpcFuture<RpcResponse> future = new RpcFuture<>(new DefaultPromise<RpcResponse>(new DefaultEventLoop()));
        RequestHolder.REQUEST_MAP.put(requestId,future);
        //发送数据
        nettyClient.sendRequest(reqProtocol);
        //异步等待返回结果 get()是异步的
        return future.getPromise().get().getData();
    }
}
