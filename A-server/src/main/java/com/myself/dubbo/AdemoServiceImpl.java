package com.myself.dubbo;

import com.myself.service.AdemoService;

/**
 * @author: wangteng
 * @description:
 * @date:2019/3/19
 */

public class AdemoServiceImpl implements AdemoService{

    @Override
    public String helloB(String name) {
        String s = "hello, B-server";
        System.out.println("hello, B-server");
        return s;
    }
}
