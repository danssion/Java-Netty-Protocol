package com.dx.netty.codec;

import com.dx.netty.codec.MessageRecordDecode;
import com.dx.netty.codec.MessageRecordEncode;
import com.dx.netty.opcode.OpCode;
import com.dx.netty.protocol.Header;
import com.dx.netty.protocol.MessageRecord;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LoggingHandler;

/**
 * @Projecet JavaExample-DdsignPattern  @Package com.dx.netty
 * @Copyright 2020 ~ 20**  @Author: dx
 * @Date: 2021/12/12 22:05
 * @Desc: 仅供测试
 */
@Deprecated
public class MainTest {
    public static void main(String[] args) {

        encodeExp();
        decodeExp();
//        packetOpExp();

    }

    public static void encodeExp() {
        //netty用来改进单元测试提供的  channelHandler 单元测试
        EmbeddedChannel channel = new EmbeddedChannel(
                //netty 中提供的日志处理器
                new LoggingHandler(),
                new MessageRecordDecode(),
                new MessageRecordEncode()
        );

        //定义消息内容
        Header header = new Header();
        header.setSessionId(12345678);
        header.setReqType(OpCode.RES.getCode());

        MessageRecord record = new MessageRecord();
        record.setHeader(header);
        record.setBody("Hello world~!");

        // 1 编码演示
        channel.writeOutbound(record);// 写出去,使用编码
    }

    // 2 解码演示
    public static void decodeExp() {
        //channelHandler 单元测试
        EmbeddedChannel channel = new EmbeddedChannel(
                //netty 中提供的日志处理器
                new LoggingHandler(),
                new MessageRecordDecode(),
                new MessageRecordEncode()
        );
        /**
         * 0~ 7 sessionId
         * 8 ReqTyoe
         * 9 ~ c  Length
         *
         *          +-------------------------------------------------+
         *          |  0  1  2  3  4  5  6  7  8  9  a  b  c  d  e  f |
         * +--------+-------------------------------------------------+----------------+
         * |00000000| 00 00 00 00 00 bc 61 4e 01 00 00 00 00 00 00 00 |......aN........|
         * |00000010| 14 ac ed 00 05 74 00 0d 48 65 6c 6c 6f 20 77 6f |.....t..Hello wo|
         * |00000020| 72 6c 64 7e 21                                  |rld~!           |
         * +--------+-------------------------------------------------+----------------+
         */

        //定义消息内容
        Header header = new Header();
        header.setSessionId(12345678);
        header.setReqType(OpCode.RES.getCode());

        MessageRecord record = new MessageRecord();
        record.setHeader(header);
        record.setBody("Hello world~!");

        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        try {
            // 先编码输入数据
            new MessageRecordEncode().encode(null,record,buf);
            channel.writeInbound(buf);//读取消息内容 读取到消息内容，进行解密
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 3 拆包连包 演示
    public static void packetOpExp() {
        //channelHandler 单元测试
        EmbeddedChannel channel = new EmbeddedChannel(
                //拆包连包 使用， 偏移量Offset 9 = 8 + 1
                new LengthFieldBasedFrameDecoder(1024*1024,9,4,0,0),
                //netty 中提供的日志处理器
                new LoggingHandler(),
                new MessageRecordDecode(),
                new MessageRecordEncode()
        );

        //定义消息内容
        Header header = new Header();
        header.setSessionId(12345678);
        header.setReqType(OpCode.RES.getCode());

        MessageRecord record = new MessageRecord();
        record.setHeader(header);
        record.setBody("Hello world~!");

        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        try {
            new MessageRecordEncode().encode(null,record,buf);
            //拆包
            ByteBuf bb1 = buf.slice(0,7);  // 数据包1
            ByteBuf bb2 = buf.slice(7,buf.readableBytes()-7); // 数据包2
            //新增一个引用，需要调用retain  ，减少释放调用  bb1.release();
            bb1.retain();

            channel.writeInbound(bb1);
            channel.writeInbound(bb2);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
