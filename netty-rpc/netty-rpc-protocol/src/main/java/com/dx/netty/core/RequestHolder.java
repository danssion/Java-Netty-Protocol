package com.dx.netty.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author duanxiang  duanxiang@haodelian.com
 * @version 1.0
 * @date 2022/3/28 21:45
 * @desc Java-Netty
 */
public class RequestHolder {
    public static final AtomicLong REQUEST_ID = new AtomicLong();

    public static final Map<Long,RpcFuture> REQUEST_MAP = new ConcurrentHashMap<>();
}
