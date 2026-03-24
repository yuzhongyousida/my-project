package com.demo.product.controller;

import com.demo.product.entity.User;
import com.demo.product.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @FileName UserController
 * @Description
 * @Author WangTeng
 * @date 2026-03-23
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    // 根据ID查询用户：GET /user/1
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    // 查询所有用户：GET /user/list
    @GetMapping("/list")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // 新增用户：POST /user
    @PostMapping
    public String addUser(@RequestBody User user) {
        boolean result = userService.addUser(user);
        return result ? "用户新增成功" : "用户新增失败";
    }

}
