package com.dx.netty.spring.service;

import com.dx.netty.core.RpcRequest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author duanxiang  duanxiang@haodelian.com
 * @version 1.0
 * @date 2022/4/4 21:29
 * @desc 委托对象，定义好 对象bean 与 method 的映射关系
 *      获取到结果 在 processor 中直接调用
 */
public class Mediator {
    public static Map<String ,BeanMethod> beanMethodMap = new ConcurrentHashMap<>();

    /**
     * 单例方式构建示例对象
     */
    private volatile static Mediator instance = null;
    private Mediator() {}

    public static Mediator getInstance() {
        if (instance == null) {
            synchronized (Mediator.class){
                if (instance == null) {
                    instance = new Mediator();
                }
            }
        }
        return instance;
    }

    public Object processor(RpcRequest request) {
        String key = request.getClassName()+"."+request.getMethodName();
        BeanMethod beanMethod = Mediator.beanMethodMap.get(key);

        if (beanMethod == null) {
            return null;
        }
        Object bean = beanMethod.getBean();
        Method method = beanMethod.getMethod();

        try {
            return method.invoke(bean,request.getParams());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }
}
