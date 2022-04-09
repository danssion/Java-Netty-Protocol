package com.dx.netty.loadbalance;

import java.util.List;

/**
 * @author duanxiang  duanxiang@haodelian.com
 * @version 1.0
 * @date 2022/4/9 15:33
 * @desc Java-Netty
 */
public interface ILoadBalance <T>{
    T select(List<T> servers);
}
