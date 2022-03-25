package com.dx.netty.codec;

import com.dx.netty.protocol.Header;
import com.dx.netty.protocol.MessageRecord;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.log4j.Log4j2;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.List;

/**
 * @Projecet JavaExample-DdsignPattern
 * @Package com.dx.netty.codec
 * @Desc: 解码器
 *
 *
 * @Copyright 2020 ~ 20**
 * @Author: dx
 * @Date: 2021/12/10 23:07
 * @Version V1.0
 */
@Log4j2
public class MessageRecordDecode extends ByteToMessageDecoder {

    /**
     * 协议： sessionId | reqType | Content-Length | Content
     * 前三部分  是 header
     *
     * @param channelHandlerContext
     * @param byteBuf
     * @param list
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        MessageRecord record = new MessageRecord();
        //ByteBuf 接收到的消息报文
        Header header = new Header();
        header.setSessionId(byteBuf.readLong());//读取 8 个字节
        header.setReqType(byteBuf.readByte());
        header.setLength(byteBuf.readInt());//读取 4 个字节长度的消息内容长度
        record.setHeader(header);
        if (header.getLength() > 0) {
            byte[] content = new byte[header.getLength()];
            byteBuf.readBytes(content);

            /**
             * java 原生对象流
             */
            //获取的 content 数据可能是个序列化数据
            ByteArrayInputStream bis = new ByteArrayInputStream(content);
            ObjectInputStream ois = new ObjectInputStream(bis);
//            ois.readObject(); // 得到一个反序列化的数据

            record.setBody(ois.readObject());
            log.info("反序列化的结果："+record);
            list.add(record);
        } else {
            log.info("消息内容为兄");
        }

    }
}
