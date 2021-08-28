package com.myself.bserver.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@NotNull(message = "入参不能为空！")
public class UserInfoDTO implements Serializable {

    private static final long serialVersionUID = -3210225686433388649L;

    private Long id;

    @NotBlank(message = "学生名称不能为空！")
    private String userName;

    @Min(value = 1, message = "学生年龄必须大于等于1！")
    private Integer age;

    @Min(value = 0, message = "学生成绩必须大于等于0！")
    private Integer score;

    private Date createTime;

    private String createBy = "wangteng05";

    private Date updateTime;

    private String updateBy;

}