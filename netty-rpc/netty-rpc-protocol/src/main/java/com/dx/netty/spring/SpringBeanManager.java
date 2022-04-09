package com.dx.netty.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author duanxiang  duanxiang@haodelian.com
 * @version 1.0
 * @date 2022/3/25 下午9:55
 * @desc Java-Netty
 */
@Deprecated
@Component
public class SpringBeanManager implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringBeanManager.applicationContext = applicationContext;
    }

    /**
     * 通过类 拿到示例
     * @param clzz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clzz) {
        return applicationContext.getBean(clzz);
    }
}
