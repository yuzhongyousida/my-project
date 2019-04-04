package com.myself.aop.proxy;

import com.myself.aop.server.IHello;
import com.myself.aop.Logger;

/**
 * @author: wangteng
 * @description: 静态代理
 * @date:2018/7/26
 */
public class StaticProxyHello implements IHello {

    private IHello hello;

    public StaticProxyHello(IHello hello) {
        super();
        this.hello = hello;
    }
    @Override
    public void sayHello(String str) {
        // 代码增强
        Logger.start();

        hello.sayHello(str);

        // 代码增强
        Logger.end();
    }
}
