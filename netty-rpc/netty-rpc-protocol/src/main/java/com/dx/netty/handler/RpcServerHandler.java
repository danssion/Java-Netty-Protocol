package com.dx.netty.handler;

import com.dx.netty.constant.ReqType;
import com.dx.netty.core.Header;
import com.dx.netty.core.RpcProtocol;
import com.dx.netty.core.RpcRequest;
import com.dx.netty.core.RpcResponse;
import com.dx.netty.spring.SpringBeanManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author duanxiang  duanxiang@haodelian.com
 * @version 1.0
 * @date 2022/3/25 下午9:43
 * @desc Java-Netty
 */
public class RpcServerHandler extends SimpleChannelInboundHandler<RpcProtocol<RpcRequest>> {
    @Override
    protected void channelRead0(ChannelHandlerContext chc, RpcProtocol<RpcRequest> msg) throws Exception {
        RpcProtocol<RpcResponse> resPrtc = new RpcProtocol();
        Header header = resPrtc.getHeader();
        header.setReqType(ReqType.RESPONSE.code());
        Object ret = invoke(msg.getContent());
        resPrtc.setHeader(header);
        RpcResponse response = new RpcResponse();
        response.setData(ret);
        response.setMsg("success");
        resPrtc.setContent(response);

        chc.writeAndFlush(resPrtc);
    }

    /**
     * rpc 反射的调用
     * @param request
     * @return
     */
    private Object invoke(RpcRequest request) {
        /**
         * 反射加载
         */
        try {
            Class<?> clazz = Class.forName(request.getClassName());
            Object bean = SpringBeanManager.getBean(clazz);
            Method method = clazz.getDeclaredMethod(request.getMethodName(), request.getParamsTypes());
            /**
             * 反射调用
             */
            return method.invoke(bean,request.getParams());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
