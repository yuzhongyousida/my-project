package com.myself.bserver.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class UserInfoVO implements Serializable {
    private static final long serialVersionUID = 1272909634332477247L;

    private Long id;

    private String userName;

    private Integer age;

    private Integer score;

    private Date createTime;

    private String createBy;

    private Date updateTime;

    private String updateBy;

}