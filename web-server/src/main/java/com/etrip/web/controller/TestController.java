package com.etrip.web.controller;

import com.etrip.dto.SensitiveWordDto;
import com.etrip.model.User;
import com.etrip.service.SensitiveWordService;
import com.etrip.service.UserService;
import com.etrip.util.common.TextFilterUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/4/14.
 */
@Controller
@RequestMapping("/testController")
public class TestController {
    public static final Logger logger = Logger.getLogger(TestController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private SensitiveWordService sensitiveWordService;


    @RequestMapping("/testLog.do")
    public void testLog(){
        try {
            User user = new User();
            user.setUserName("wangteng");
            user.setPassword("111111");
            userService.insert(user);

//            userService.deleteByUserName("wangteng");

            List<User> userList = userService.getUserByUserName("wangteng");
            if(!CollectionUtils.isEmpty(userList)){
                for (User user1 : userList){
                    System.out.println(user1.getUserName());
                    System.out.println(user1.getPassword());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @RequestMapping("/test1.do")
    public  void test1(){
        try {

//            InputStream in = TestController.class.getClassLoader().getResourceAsStream("properties/其他词库.txt");
//            BufferedReader reader = new BufferedReader(new InputStreamReader(in,"utf-8"));
//            String line;
//            while ((line = reader.readLine()) != null) {
//                System.out.println(line.trim());
//
//                SensitiveWordDto sensitiveWordDto = new SensitiveWordDto();
//                sensitiveWordDto.setSensitiveWord(line.trim());
//                sensitiveWordDto.setSensitiveWordType(6);
//                sensitiveWordService.insertSensitiveWord(sensitiveWordDto);
//            }

            long startMiliscond  = System.currentTimeMillis();


            String s = "福音会";
            String result = TextFilterUtil.replaceSensitiveWord(s);

            long endMiliscond  = System.currentTimeMillis();
            System.out.println("----------时间花费："+(endMiliscond-startMiliscond)+"ms");
        }catch (Exception e){
            logger.error("获取敏感词库的文件内容方法异常，TextFilterUtil.getSensitiveWordFileContent(),异常信息为：" + e);
        }

    }


}