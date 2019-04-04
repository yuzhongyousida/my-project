package com.myself.rpc.service.impl;

import com.myself.rpc.service.HelloService;

/**
 * Created by Administrator on 2017/8/1.
 * Java实现RPC框架——服务提供者接口实现类
 */
public class HelloServiceImpl implements HelloService {


    @Override
    public String sayHi(String name) {
        return "Hi, " + name;
    }
}
