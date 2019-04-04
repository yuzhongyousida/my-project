package com.myself.dto;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/18.
 */
public class UserDto implements Serializable{
    private static final long serialVersionUID = -3406978819297531941L;

    private String userName;
    private String password;
    private Integer age;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
