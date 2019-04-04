package com.myself.rpc.registerCenter;

import java.io.IOException;

/**
 * Created by Administrator on 2017/8/1.
 * Java实现RPC框架——注册中心接口定义
 */
public interface Server {

    /**
     * 关闭注册中心
     */
    public void stop();

    /**
     * 注册中心启动
     * @throws IOException
     */
    public void start() throws IOException;

    /**
     * rpc服务类想注册中心注册
     * @param serviceInterface
     * @param impl
     */
    public void register(Class serviceInterface, Class impl);

    /**
     * 注册中心是否是运行状态
     * @return
     */
    public Boolean isRunning();

    /**
     * 获取注册中心端口
     * @return
     */
    public int getPort();

}
