package com.dx.netty.service;

import com.dx.netty.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author duanxiang  duanxiang@haodelian.com
 * @version 1.0
 * @date 2022/3/22 下午9:52
 * @desc Java-Netty
 */
@Service
@Slf4j
public class UserServiceImpl implements IUserService {

    @Override
    public String saveUser(String name) {
        log.info("begin save user:{}",name);

        return "save User success:"+name;
    }

}
