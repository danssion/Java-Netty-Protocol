package com.dx.netty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author duanxiang  duanxiang@haodelian.com
 * @version 1.0
 * @date 2022/4/7 21:36
 * @desc Java-Netty   内置 tomcat 方式
 */
@ComponentScan(basePackages = {"com.dx.netty.spring.reference","com.dx.netty.controller","com.dx.netty.annotation"})
@SpringBootApplication
public class NettyConsumerMain {
    public static void main(String[] args) {
        SpringApplication.run(NettyConsumerMain.class,args);
    }
}
