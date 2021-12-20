package com.dx.netty.codec;

import com.dx.netty.protocol.Header;
import com.dx.netty.protocol.MessageRecord;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.log4j.Log4j2;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Handler;

/**
 * @Projecet JavaExample-DdsignPattern
 * @Package com.dx.netty.codec
 * @Desc:  编码器
 * @Copyright 2020 ~ 20**
 * @Author: dx
 * @Date: 2021/12/10 23:07
 * @Version V1.0
 */
@Log4j2
public class MessageRecordEncode extends MessageToByteEncoder<MessageRecord> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, MessageRecord msg, ByteBuf out) throws Exception {
        log.info("===== 开始进行消息编码 =====");

        Header header = msg.getHeader(); // 消息的 header 部分，按顺序写出
        out.writeLong(header.getSessionId()); // 首先写 8 字节的 sessionId
        out.writeByte(header.getReqType());
//        out.writeInt(header.getLength()); //消息长度 在下面设置

        Object body = msg.getBody();
        if (body != null) {
            //内存输出流
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            //内存对象输出流
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(body);
            byte[] bytes=bos.toByteArray();
            out.writeInt(bytes.length);//写消息长度
            out.writeBytes(bytes);
        } else {
            out.writeInt(0);//消息长度为0
            log.info("");
        }
    }
}
