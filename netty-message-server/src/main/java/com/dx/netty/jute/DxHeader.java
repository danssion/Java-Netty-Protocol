package com.dx.netty.jute;

import lombok.Data;
import org.apache.jute.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * @author duanxiang  duanxiang@haodelian.com
 * @version 1.0
 * @date 2022/3/17 下午9:13
 * @desc Java-Netty-Protocol
 * 基于 zookeeper jute 协议的 序列化 反序列化
 */

@Data
public class DxHeader implements Record {
    private long sessionId;
    private String type;

    public DxHeader() {
    }

    public DxHeader(long sessionId, String type) {
        this.sessionId = sessionId;
        this.type = type;
    }

    @Override
    public void serialize(OutputArchive outputArchive, String s) throws IOException {
        //从 s 标记为开始 进行序列化
        outputArchive.startRecord(this,s);
        outputArchive.writeLong(this.sessionId, "sessionId");
        outputArchive.writeString(this.type,"type");
        //结束位置
        outputArchive.endRecord(this,s);
    }

    @Override
    public void deserialize(InputArchive archive, String tag) throws IOException {
        archive.startRecord(tag);
        this.sessionId = archive.readLong( "sessionId");
        this.type = archive.readString("type");
        //结束位置
        archive.endRecord(tag);
    }

    public static void main(String[] args) throws IOException {
        final String tag = "header";
        //用内存保存  序列化以后的数据
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BinaryOutputArchive boa = BinaryOutputArchive.getArchive(baos);
//        序列化后保存到 BinaryOutputArchive 中，打tag header
        new DxHeader(123456,"dx header").serialize(boa,tag);
        // NIO 的对象，应用空间的缓冲区
        ByteBuffer byteBuffer = ByteBuffer.wrap(baos.toByteArray());
        System.out.println(byteBuffer);

        // 反序列化
        ByteArrayInputStream bbis = new ByteArrayInputStream(baos.toByteArray());
        BinaryInputArchive bbia = BinaryInputArchive.getArchive(bbis);

        DxHeader header = new DxHeader();
        header.deserialize(bbia, tag);
        System.out.println(header);

    }
}
