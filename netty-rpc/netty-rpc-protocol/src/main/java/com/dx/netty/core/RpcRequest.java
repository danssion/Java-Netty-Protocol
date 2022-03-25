package com.dx.netty.core;

import lombok.Data;

import java.io.Serializable;

/**
 * @author duanxiang  duanxiang@haodelian.com
 * @version 1.0
 * @date 2022/3/23 下午9:23
 * @desc Java-Netty
 */
@Data
public class RpcRequest implements Serializable {
    //请求目标的 类名 、  方法名
    // 进行反射调用
    private String className;
    private String methodName;

    // 参数
    private Object[] params;
    // 参数类型
    private Class<?>[] paramsTypes;


}
