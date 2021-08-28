package com.myself.bserver.web.controller.user;

import com.myself.bserver.dto.UserInfoDTO;
import com.myself.bserver.service.UserInfoService;
import com.myself.bserver.vo.Result;
import com.myself.bserver.vo.UserInfoVO;
import com.myself.bserver.web.controller.base.BaseController;
import com.myself.constant.CommonConstant;
import com.myself.enums.ResultEnum;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wangteng05
 * @description: UserController类
 * @date 2021/5/18 18:23
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
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


    /**
     * 根据条件，获取用户信息
     * @param dto
     * @param bindingResult
     * @return
     */
    @ResponseBody
    @RequestMapping("/getUserInfo")
    public Result getUserInfo(@RequestBody @Valid UserInfoDTO dto, BindingResult bindingResult){
        // 生成一个默认失败的result实例
        Result result = new Result();

        // 参数校验
        if (bindingResult.hasErrors() && CollectionUtils.isNotEmpty(bindingResult.getAllErrors())){
            StringBuffer errorMessageBuffer = new StringBuffer(CommonConstant.PARAM_CHECK_MESSAGE_START);
            bindingResult.getAllErrors().stream()
                    .filter(error->!StringUtils.isBlank(error.getDefaultMessage()))
                    .forEach(error->{
                        errorMessageBuffer.append(error.getDefaultMessage());
                    });
            result.setMessage(errorMessageBuffer.toString());
            return result;
        }

        UserInfoVO vo = new UserInfoVO();
        vo.setAge(10);
        vo.setUserName("wang");
        vo.setScore(100);
        vo.setCreateBy("wangteng");
        vo.setCreateTime(new Date());

        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMessage(ResultEnum.SUCCESS.getDescription());
        result.setData(vo);

        return result;
    }

}
