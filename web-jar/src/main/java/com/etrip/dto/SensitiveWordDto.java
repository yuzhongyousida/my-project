package com.etrip.dto;

import java.io.Serializable;
import java.util.Date;

public class SensitiveWordDto implements Serializable{
    private static final long serialVersionUID = 4039866081980955209L;


    private Long id;

    private Integer sensitiveWordType;

    private String sensitiveWord;

    private Integer isDelete;

    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSensitiveWordType() {
        return sensitiveWordType;
    }

    public void setSensitiveWordType(Integer sensitiveWordType) {
        this.sensitiveWordType = sensitiveWordType;
    }

    public String getSensitiveWord() {
        return sensitiveWord;
    }

    public void setSensitiveWord(String sensitiveWord) {
        this.sensitiveWord = sensitiveWord == null ? null : sensitiveWord.trim();
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}