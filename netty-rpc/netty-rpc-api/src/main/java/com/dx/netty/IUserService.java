package com.dx.netty;

/**
 * @author duanxiang  duanxiang@haodelian.com
 * @version 1.0
 * @date 2022/3/21 下午10:33
 * @desc Java-Netty
 */
public interface IUserService {
    /**
     * 保存用户信息
     * @param name
     * @return
     */
    String saveUser(String name);
}
