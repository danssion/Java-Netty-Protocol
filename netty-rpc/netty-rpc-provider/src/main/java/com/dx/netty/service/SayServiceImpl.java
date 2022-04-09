package com.dx.netty.service;

import com.dx.netty.ISayService;
import com.dx.netty.annotation.DxRemoteService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author duanxiang  duanxiang@haodelian.com
 * @version 1.0
 * @date 2022/4/7 22:16
 * @desc Java-Netty
 */
@Slf4j
@DxRemoteService
public class SayServiceImpl implements ISayService {
    @Override
    public String say() {
        return "Hello ~! From SayService";
    }
}
