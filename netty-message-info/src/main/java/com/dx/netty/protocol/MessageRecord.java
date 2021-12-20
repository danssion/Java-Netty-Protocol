package com.dx.netty.protocol;

import lombok.Data;

/**
 * @Projecet JavaExample-DdsignPattern
 * @Package com.dx.netty.protocol
 * @Desc:
 *
 * @Copyright 2020 ~ 20**
 * @Author: dx
 * @Date: 2021/12/10 23:00
 * @Version V1.0
 */
@Data
public class MessageRecord {
    private Header header;
    private Object body;
}
