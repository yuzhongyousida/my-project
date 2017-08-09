package com.etrip.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/18.
 */
public class User implements Serializable{

    private static final long serialVersionUID = 1835115164855723296L;

    private String userName;
    private String password;

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
