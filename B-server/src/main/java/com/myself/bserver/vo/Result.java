package com.myself.bserver.vo;

import com.myself.enums.ResultEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wangteng05
 * @description: Result类
 * @date 2021/8/27 19:32
 */
@Data
public class Result implements Serializable {

    private static final long serialVersionUID = -6977073041552236411L;

    /**
     * 无参构造器，默认生成的是失败类型的result实例
     */
    public Result(){
        this.code = ResultEnum.FAIL.getCode();
        this.message = ResultEnum.FAIL.getDescription();
    }

    /**
     * 错误码（0：正常，负值：异常）
     */
    private Integer code;

    /**
     * 提示信息
     */
    private String message;

    /**
     * 数据对象
     */
    private Object data;
}
