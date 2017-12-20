package com.etrip.service;

import com.etrip.model.User;

import java.util.List;

/**
 * Created by Administrator on 2017/5/18.
 */
public interface UserService {


    public void insert(User user);

    public List<User> getUserByUserName(String userName);

    public void updateByUserName(User user);

    public void deleteByUserName(String userName);

}
