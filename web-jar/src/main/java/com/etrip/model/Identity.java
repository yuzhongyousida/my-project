package com.etrip.model;

import java.util.List;

/**
 * @author: wangteng
 * @description:
 * @date:2018/4/19
 */
public class Identity {

    private String idNo;

    private List<String> schoolingNames;

    private Integer age;

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public List<String> getSchoolingNames() {
        return schoolingNames;
    }

    public void setSchoolingNames(List<String> schoolingNames) {
        this.schoolingNames = schoolingNames;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
