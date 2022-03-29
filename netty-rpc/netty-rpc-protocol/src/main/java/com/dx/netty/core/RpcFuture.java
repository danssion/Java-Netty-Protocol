package com.dx.netty.core;

import io.netty.util.concurrent.Promise;
import lombok.Data;

/**
 * @author duanxiang  duanxiang@haodelian.com
 * @version 1.0
 * @date 2022/3/28 22:11
 * @desc Java-Netty
 */
@Data
public class RpcFuture<T> {

    //netty 提供的一个api ，接收 异步future ，
    // 再从 promise拿到 结果
    private Promise<T> promise;


    public RpcFuture(Promise<T> promise) {
        this.promise = promise;
    }

}
