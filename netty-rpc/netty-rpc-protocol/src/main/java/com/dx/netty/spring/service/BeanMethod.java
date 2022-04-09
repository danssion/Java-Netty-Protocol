package com.dx.netty.spring.service;

import lombok.Data;

import java.lang.reflect.Method;

/**
 * @author duanxiang  duanxiang@haodelian.com
 * @version 1.0
 * @date 2022/4/4 21:27
 * @desc
 */
@Data
public class BeanMethod {
    private Object bean;
    private Method method;
}
