package com.myself.bserver.web.controller.user;

import com.myself.bserver.dto.UserInfoDTO;
import com.myself.bserver.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author wangteng05
 * @description: UserController类
 * @date 2021/5/18 18:23
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping("/hello")
    public String sayHello(){
        UserInfoDTO dto = new UserInfoDTO();
        dto.setAge(10);
        dto.setUserName("wang");
        dto.setScore(100);
        dto.setCreateBy("wangteng");
        dto.setCreateTime(new Date());

        userInfoService.insertSelective(dto);
        return "hello";
    }
}
