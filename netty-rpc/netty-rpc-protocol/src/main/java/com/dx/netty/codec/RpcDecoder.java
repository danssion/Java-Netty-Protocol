package com.dx.netty.codec;

import com.dx.netty.constant.ReqType;
import com.dx.netty.constant.RpcContent;
import com.dx.netty.core.Header;
import com.dx.netty.core.RpcProtocol;
import com.dx.netty.core.RpcRequest;
import com.dx.netty.core.RpcResponse;
import com.dx.netty.serial.ISerializer;
import com.dx.netty.serial.SerializerManager;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author duanxiang  duanxiang@haodelian.com
 * @version 1.0
 * @date 2022/3/23 下午10:07
 * @desc Java-Netty
 */
@Slf4j
public class RpcDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> out) throws Exception {
        log.info("========== begin RpcRecorder ==========");

        if(in.readableBytes() < RpcContent.HEAD_TOTAL_LEN) {
            return;
        }
        // 标记读取开始的索引
        in.markReaderIndex();
        // 读取2 字节的 magic
        short mac = in.readShort();
        if(mac != RpcContent.MAGIC) {
            throw new IllegalArgumentException("Illegal request parameter 'magic' ,"+mac);
        }

        //读取 1 个字节的序列化列席
        byte serialType = in.readByte();
        // 1 个字节的消息类型
        byte reqType = in.readByte();
        // 请求id
        long requestId = in.readLong();
        // 报文长度
        int dataLength = in.readInt();
        // 剩余的有效字节数 是否小于 报文长度，小于时可读区域不够报文
        // 有可能是 ping 的报文，需要还原 继续向下传递
        if(in.readableBytes() < dataLength) {
            // 还原index
            in.resetReaderIndex();
            return ;
        }

        // 读取消息体的内容
        byte[] content = new byte[dataLength];
        in.readBytes(content);

        Header header = new Header(mac,serialType,reqType,requestId,dataLength);
        ISerializer serializer = SerializerManager.getSerializer(serialType);
        ReqType rt = ReqType.findByCode(reqType);

        switch (rt) {
            case REQUEST:
                RpcRequest request = serializer.deserialize(content, RpcRequest.class);
                RpcProtocol<RpcRequest> rpcRqProtocol = new RpcProtocol<>();
                rpcRqProtocol.setHeader(header);
                rpcRqProtocol.setContent(request);
                out.add(rpcRqProtocol);
                break;
            case RESPONSE:
                RpcResponse response = serializer.deserialize(content, RpcResponse.class);
                RpcProtocol<RpcResponse> rpcRspProtocol = new RpcProtocol<>();
                rpcRspProtocol.setHeader(header);
                rpcRspProtocol.setContent(response);
                out.add(rpcRspProtocol);
                break;
            case HEARTBEAT:
                //TODO
                break;
            default:
                break;
        }






    }
}
