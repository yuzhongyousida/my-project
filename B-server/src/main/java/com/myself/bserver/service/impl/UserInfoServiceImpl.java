package com.myself.bserver.service.impl;

import com.myself.bserver.dao.UserInfoDAO;
import com.myself.bserver.dto.UserInfoDTO;
import com.myself.bserver.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wangteng05
 * @description: 用户信息服务类
 * @date 2021/5/18 19:52
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserInfoDAO userInfoDAO;


    @Override
    public int deleteByPrimaryKey(Long id) {
        return userInfoDAO.deleteByPrimaryKey(id);
    }

    @Override
    public int insertSelective(UserInfoDTO record) {
        return userInfoDAO.insertSelective(record);
    }

    @Override
    public UserInfoDTO selectByPrimaryKey(Long id) {
        return userInfoDAO.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(UserInfoDTO record) {
        return userInfoDAO.updateByPrimaryKeySelective(record);
    }
}
