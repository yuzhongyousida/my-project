package com.myselft.bserver.web.controller.login;

import com.alipay.sofa.rpc.config.ConsumerConfig;
import com.alipay.sofa.rpc.config.ProviderConfig;
import com.alipay.sofa.rpc.config.ServerConfig;
import com.myselft.bserver.service.HelloService;
import com.myselft.bserver.service.impl.HelloServiceImpl;
import com.myselft.bserver.web.controller.base.BaseController;
import org.springframework.stereotype.Controller;

/**
 * @ClassNane LoginController
 * @Description
 * @Author wangteng
 * @Date 2020-09-25 15:52
 * @Version 1.0.0
 */
@Controller("/login")
public class LoginController extends BaseController {


    public static void main(String[] args) {
        quickStartServer();

        quickStartClient();

    }


    public static void quickStartServer(){
        ServerConfig serverConfig = new ServerConfig()
                .setProtocol("bolt") // 设置一个协议，默认bolt
                .setPort(12200) // 设置一个端口，默认12200
                .setDaemon(false); // 非守护线程

        ProviderConfig<HelloService> providerConfig = new ProviderConfig<HelloService>()
                .setInterfaceId(HelloService.class.getName()) // 指定接口
                .setRef(new HelloServiceImpl()) // 指定实现
                .setServer(serverConfig); // 指定服务端

        providerConfig.export(); // 发布服务
    }


    public static void quickStartClient(){
        ConsumerConfig<HelloService> consumerConfig = new ConsumerConfig<HelloService>()
                .setInterfaceId(HelloService.class.getName()) // 指定接口
                .setProtocol("bolt") // 指定协议
                .setDirectUrl("bolt://127.0.0.1:12200"); // 指定直连地址
        // 生成代理类
        HelloService helloService = consumerConfig.refer();

        while (true) {
            System.out.println(helloService.sayHello());
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
            }
        }
    }



}
