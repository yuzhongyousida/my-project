package com.etrip.aop;

import com.etrip.aop.proxy.CglibDynamicProxy;
import com.etrip.aop.server.Hello;

/**
 * @author: wangteng
 * @description:
 * @date:2018/7/26
 */
public class TestCase {

    public static void main(String[] args) {
//        IHello hello = new StaticProxyHello(new HelloImpl());
//        hello.sayHello("小明");

//        IHello hello = (IHello) new JdkDynamicProxy().createProxy(new HelloImpl());
//        hello.sayHello("小黄");

        Hello hello = (Hello) new CglibDynamicProxy().createProxy(new Hello());
        hello.sayHello("小马");
    }

}
