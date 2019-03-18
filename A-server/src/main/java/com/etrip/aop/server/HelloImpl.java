package com.etrip.aop.server;

/**
 * @author: wangteng
 * @description:
 * @date:2018/7/26
 */
public class HelloImpl implements IHello{
    @Override
    public void sayHello(String name) {
        System.out.println("hello, " + name);
    }

}
