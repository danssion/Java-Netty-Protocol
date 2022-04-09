package com.dx.netty.controller;

import com.dx.netty.ISayService;
import com.dx.netty.IUserService;
import com.dx.netty.annotation.DxRemoteReference;
import com.dx.netty.annotation.DxRemoteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author duanxiang  duanxiang@haodelian.com
 * @version 1.0
 * @date 2022/4/5 19:00
 * @desc  目的：扫描Controller 动态生成 userService bean
 *          动态代理相关类需要
 */
@RestController
public class HelloController {

    @DxRemoteReference
    IUserService userService;

    @DxRemoteReference
    ISayService sayService;

    @GetMapping("/say")
    public String say() {
        return userService.saveUser("dx");
    }

    @GetMapping("/hi")
    public String hello() {
        return  sayService.say();
    }
}
