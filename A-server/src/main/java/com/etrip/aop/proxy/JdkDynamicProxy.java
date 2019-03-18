package com.etrip.aop.proxy;

import com.etrip.aop.Logger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author: wangteng
 * @description:
 * @date:2018/7/26
 */
public class JdkDynamicProxy implements InvocationHandler {

    /**
     * 目标对象
     */
    private Object target;

    /**
     * 通过反射实例化目标类对象
     * @param object
     * @return
     */
    public Object createProxy(Object object) {
        this.target = object;
        return Proxy.newProxyInstance(this.target.getClass().getClassLoader(), this.target.getClass().getInterfaces(), this);
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;

        if ("sayHello".equals(method.getName())){
            Logger.start();
            result = method.invoke(this.target, args);
            Logger.end();
        }else {
            result = method.invoke(this.target, args);
        }
        return result;
    }
}
