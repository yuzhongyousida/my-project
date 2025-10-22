package com.myself.service;

import com.myself.entity.User;

import java.util.List;

public interface UserService {
    // 创建用户
    User createUser(User user);

    // 根据ID获取用户
    User getUserById(Long id);

    // 获取所有用户
    List<User> getAllUsers();

    // 更新用户
    User updateUser(User user);

    // 删除用户
    boolean deleteUser(Long id);

     // 根据条件查询用户
    List<User> getUsersByConditions(User user);
}
