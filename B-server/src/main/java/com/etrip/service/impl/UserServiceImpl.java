package com.etrip.service.impl;

import com.etrip.dao.mongodb.UserMongodbDao;
import com.etrip.model.User;
import com.etrip.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/5/18.
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMongodbDao userMongodbDao;


    /**
     * 新增
     * @param user
     */
    @Override
    public void insert(User user){
        userMongodbDao.insert(user);
    }


    /**
     * 根据用户名查询用户列表
     * @param userName
     * @return
     */
    @Override
    public List<User> getUserByUserName(String userName) {
        return userMongodbDao.selectListByUserName(userName);
    }


    /**
     * 根据userName更新密码
     * @param user
     */
    @Override
    public void updateByUserName(User user) {
        userMongodbDao.updateByUserName(user);
    }

    /**
     * 删除
     * @param userName
     */
    @Override
    public void deleteByUserName(String userName) {
        if(StringUtils.isEmpty(userName)){
            return;
        }
        User user = new User();
        user.setUserName(userName);
        userMongodbDao.deleteByUserName(user);
    }


}
