package com.dx.netty.codec;

import com.dx.netty.core.Header;
import com.dx.netty.core.RpcProtocol;
import com.dx.netty.serial.ISerializer;
import com.dx.netty.serial.SerializerManager;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author duanxiang  duanxiang@haodelian.com
 * @version 1.0
 * @date 2022/3/24 下午10:10
 * @desc Java-Netty
 */
@Slf4j
public class RpcEncoder extends MessageToByteEncoder<RpcProtocol<Object>> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, RpcProtocol<Object> msg, ByteBuf out) throws Exception {
        log.info("========== begin RpcEncoder ==========");

        Header header = msg.getHeader();
        out.writeShort(header.getMagic());
        out.writeShort(header.getSerialType());
        out.writeShort(header.getReqType());
        out.writeLong(header.getRequestId());

        ISerializer serializer = SerializerManager.getSerializer(header.getSerialType());
        byte[] data = serializer.serialize(msg.getContent());
//        header.setLength(data.length);
        //写入消息长度
        out.writeInt(data.length);
        out.writeBytes(data);
    }
}
