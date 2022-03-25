package com.dx.netty.protocol;

import lombok.Data;

/**
 * @author duanxiang  duanxiang@haodelian.com
 * @version 1.0
 * @date 2022/3/14 下午10:47
 * @desc Java-Netty-Protocol
 */

@Data
public class MessageRecord {
    private Header header;
    private Object body;
}
