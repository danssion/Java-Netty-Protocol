package com.dx.netty.spring.reference;

import com.dx.netty.annotation.DxRemoteReference;
import com.dx.netty.annotation.DxRemoteService;
import com.dx.netty.spring.service.SpringRpcProviderBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author duanxiang  duanxiang@haodelian.com
 * @version 1.0
 * @date 2022/4/6 22:08
 * @desc Java-Netty
 * ApplicationContextAware 获得application 上下文信息
 * BeanClassLoaderAware 获取Bean的类转载器
 * BeanFactoryPostProcessor bean 实例化后执行回调的扩展
 *
 */

@Slf4j
public class SpringRpcReferencePostProcessor implements ApplicationContextAware, BeanClassLoaderAware, BeanFactoryPostProcessor {
    private ApplicationContext context;
    private ClassLoader classLoader;
    private RpcClientProperties properties;

    public SpringRpcReferencePostProcessor(RpcClientProperties properties) {
        this.properties = properties;
    }

    // 保存发布的引用bean的信息
    private final Map<String , BeanDefinition> rpcRefBeanDefinition = new ConcurrentHashMap<>();

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        // spring 触发回调的时候，会把示例给到
        this.classLoader = classLoader;
    }
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    //spring 容器加载了Bean的定义文件之后，并在Bean实例化之前执行
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        //循环遍历，bean 是否带有注解DxRemoteService，如果有，进行代理处理
        for(String beanName:beanFactory.getBeanDefinitionNames()) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
            String beanClassName = beanDefinition.getBeanClassName();
            if(beanClassName != null) {
                Class<?> clazz = ClassUtils.resolveClassName(beanClassName,this.classLoader);
                // 遍历类中定义的字段
                ReflectionUtils.doWithFields(clazz,this::parseRpcReference);
            }
        }


        BeanDefinitionRegistry registry = (BeanDefinitionRegistry) beanFactory;
        this.rpcRefBeanDefinition.forEach((beanName,beanDefinition) ->{
            if(context.containsBean(beanName)) {
                log.warn("SpringContext already register bean {}",beanName);
                return;
            }
            registry.registerBeanDefinition(beanName, beanDefinition);
            log.info("Registered RpcReferenceBean {} success", beanName);
        });
    }

    private void parseRpcReference(Field field) {
        DxRemoteReference dxRemoteReference = AnnotationUtils.getAnnotation(field, DxRemoteReference.class);
        //有注解，就动态生成一个工厂bean
        if (dxRemoteReference != null) {
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(SpringRpcReferenceBean.class);
            builder.setInitMethodName("init");
            builder.addPropertyValue("interfaceClass", field.getType());
            builder.addPropertyValue("serverAddress", properties.getServiceAddress());
            builder.addPropertyValue("serverPort", properties.getServicePort());
            BeanDefinition  beanDefinition = builder.getBeanDefinition();
            rpcRefBeanDefinition.put(field.getName(), beanDefinition);
        }
    }


}
