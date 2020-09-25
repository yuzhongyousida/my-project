package com.myselft.bserver.service.impl;

import com.myselft.bserver.service.HelloService;

/**
 * @ClassNane HelloServiceImpl
 * @Description
 * @Author wangteng
 * @Date 2020-09-25 15:17
 * @Version 1.0.0
 */
public class HelloServiceImpl implements HelloService {


    @Override
    public String sayHello() {
        System.out.println("hello sofa-rpc");

        return "hello";
    }
}
