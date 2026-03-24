package com.demo.product.mapper;

import com.demo.product.entity.User;
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


}
