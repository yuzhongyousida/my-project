package com.myselft.bserver.web.controller.user;

import com.myself.service.AdemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: wangteng
 * @description:
 * @date:2019/3/19
 */
@Controller
@RequestMapping("/dubboTest")
public class DubboTestController {

    @Autowired
    private AdemoService ademoService;


    @RequestMapping("/invokeA.do")
    public void invokeAservice(){
        String ret = ademoService.helloB("B-server");
        System.out.println(ret);
    }

}
