package com.myself.enums;

/**
 * Result类对应的枚举类
 */
public enum ResultEnum {
    SUCCESS(0, "成功"),
    FAIL(-1, "系统异常");

    private Integer code; // 错误码

    private String description; // 描述

    ResultEnum(Integer code, String description){
        this.code = code;
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
