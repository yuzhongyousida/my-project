package com.demo.user.mapper;

import com.demo.user.entity.User;
import com.demo.user.entity.UserQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @FileName UserMapper
 * @Description
 * @Author bytedance
 * @date 2026-03-23
 */
@Mapper
public interface UserMapper {
    // 根据ID查询用户
    User selectUserById(@Param("id") Long id);

    // 查询所有用户
    List<User> selectAllUsers();

    // 新增用户
    int insertUser(User user);

    // 根据条件查询用户列表
    List<User> selectUsersByCondition(UserQuery query);

    // 根据条件查询用户总数
    int countUsersByCondition(UserQuery query);

}