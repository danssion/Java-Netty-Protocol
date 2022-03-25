package com.dx.netty.core;

import lombok.Data;

import java.io.Serializable;

/**
 * @author duanxiang  duanxiang@haodelian.com
 * @version 1.0
 * @date 2022/3/23 下午9:29
 * @desc Java-Netty
 */
@Data
public class RpcProtocol<T> implements Serializable {
    private Header header;
    private T content;
}
