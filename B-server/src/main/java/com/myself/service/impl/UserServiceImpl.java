package com.myself.service.impl;

import com.myself.entity.User;
import com.myself.mapper.UserMapper;
import com.myself.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * @description: 创建用户
     * @params: [user]
     * @return: com.myself.entity.User
     * @author: wangteng
     * @date: 2025/10/13
     */
    @Override
    public User createUser(User user) {
        userMapper.insert(user);
        return user;
    }

    @Override
    public User getUserById(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userMapper.selectAll();
    }

    @Override
    public User updateUser(User user) {
        userMapper.update(user);
        return userMapper.selectById(user.getId());
    }

    @Override
    public boolean deleteUser(Long id) {
        return userMapper.deleteById(id) > 0;
    }

}
