package com.demo.user.service;

import com.demo.user.entity.User;
import com.demo.user.entity.UserQuery;

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

    /**
     * 根据条件查询用户列表（分页）
     * @param query 查询条件
     * @return 用户列表
     */
    List<User> queryUsers(UserQuery query);

    /**
     * 根据条件查询用户总数
     * @param query 查询条件
     * @return 用户总数
     */
    int countUsers(UserQuery query);
}