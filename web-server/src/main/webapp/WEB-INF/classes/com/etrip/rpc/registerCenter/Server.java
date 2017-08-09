package com.etrip.rpc.registerCenter;

import java.io.IOException;

/**
 * Created by Administrator on 2017/8/1.
 * Java实现RPC框架——注册中心接口定义
 */
public interface Server {

    public void stop();

    public void start() throws IOException;

    public void register(Class serviceInterface, Class impl);

    public Boolean isRunning();

    public int getPort();

}
