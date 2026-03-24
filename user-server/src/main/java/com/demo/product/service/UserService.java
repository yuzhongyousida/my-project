package com.demo.product.service;

import com.demo.product.entity.User;

import java.util.List;

/**
 * @FileName UserService
 * @Description 用户服务接口
 * @Author WangTeng
 * @date 2026-03-23
 */
public interface UserService {

    User getUserById(Long id);

    List<User> getAllUsers();

    boolean addUser(User user);
}
