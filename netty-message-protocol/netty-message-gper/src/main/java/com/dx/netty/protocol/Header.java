package com.dx.netty.protocol;

import lombok.Data;

/**
 * @author duanxiang  duanxiang@haodelian.com
 * @version 1.0
 * @date 2022/3/14 下午10:41
 * @desc Java-Netty-Protocol
 */

@Data
public class Header {

    private long sessionId; //会话id  8字节
    private byte reqType; //消息类型   1 个字节
    private int length; //消息体长度   4 个字节
}
