package com.etrip.aop;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author: wangteng
 * @description:
 * @date:2018/7/26
 */
public class CglibDynamicProxy implements MethodInterceptor{
    /**
     * 目标对象
     */
    private Object target;


    /**
     * 用CGLIB生成代理
     * @return
     */
    public Object createProxy(Object object){
        this.target = object;

        // 创建核心类
        Enhancer enhancer = new Enhancer();

        // 为其设置父类
        enhancer.setSuperclass(target.getClass());

        // 设置回调
        enhancer.setCallback(this);

        // 创建代理
        return enhancer.create();
    }


    @Override
    public Object intercept(Object proxy, Method method, Object[] args,MethodProxy methodProxy) throws Throwable {
        if("sayHello".equals(method.getName())){
            Logger.start();
            Object obj = methodProxy.invokeSuper(proxy, args);
            Logger.end();
            return obj;
        }

        return methodProxy.invokeSuper(proxy, args);
    }

}
