package com.demo.product.service.impl;

import com.demo.product.entity.User;
import com.demo.product.mapper.UserMapper;
import com.demo.product.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @FileName UserServiceImpl
 * @Description
 * @Author WangTeng
 * @date 2026-03-23
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private  UserMapper userMapper;

    @Override
    public User getUserById(Long id) {
        return userMapper.selectUserById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userMapper.selectAllUsers();
    }

    @Override
    public boolean addUser(User user) {
        return userMapper.insertUser(user) > 0;
    }
}
