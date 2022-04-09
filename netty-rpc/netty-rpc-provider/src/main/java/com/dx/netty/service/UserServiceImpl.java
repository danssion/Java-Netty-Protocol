package com.dx.netty.service;

import com.dx.netty.IUserService;
import com.dx.netty.annotation.DxRemoteService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author duanxiang  duanxiang@haodelian.com
 * @version 1.0
 * @date 2022/3/22 下午9:52
 * @desc Java-Netty
 */
//@Service
@DxRemoteService
@Slf4j
public class UserServiceImpl implements IUserService {

    @Override
    public String saveUser(String name) {
        log.info("begin save user:{}",name);
        return "save User success:"+name;
    }

}
