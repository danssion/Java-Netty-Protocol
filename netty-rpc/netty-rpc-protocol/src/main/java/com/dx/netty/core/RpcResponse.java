package com.dx.netty.core;

import lombok.Data;

import java.io.Serializable;

/**
 * @author duanxiang  duanxiang@haodelian.com
 * @version 1.0
 * @date 2022/3/23 下午9:27
 * @desc Java-Netty
 */
@Data
public class RpcResponse implements Serializable {
    // 返回的数据
    private Object data;

    // 返回的信息
    private String msg;

}
