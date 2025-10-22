package com.myself.mapper;

import com.myself.entity.User;

import java.util.List;

public interface UserMapper {
    // 新增用户
    int insert(User user);

    // 根据ID查询用户
    User selectById(Long id);

    // 查询所有用户
    List<User> selectAll();

    // 更新用户
    int update(User user);

    // 根据ID删除用户
    int deleteById(Long id);

     // 根据条件查询用户
    List<User> getUsersByConditions(User user);
}
