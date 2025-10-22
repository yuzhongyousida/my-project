package com.myself.controller;

import com.myself.entity.User;
import com.myself.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 创建用户
     */
    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }

    // 获取用户详情
    @GetMapping("/getUserById/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    // 获取所有用户
    @GetMapping("/getAllUsers")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // 更新用户
    @PutMapping("/updateUser/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        User updatedUser = userService.updateUser(user);
        return ResponseEntity.ok(updatedUser);
    }

    /**
      * @description:
      * @params:
      * @return:
      * @author: wangteng
      * @date: 2025/10/13
      */
    @DeleteMapping("/deleteUser{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        boolean deleted = userService.deleteUser(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * @description: 根据条件查询用户列表
     * @params: [user]
     * @return: org.springframework.http.ResponseEntity<java.util.List<com.myself.entity.User>>
     * @author: wangteng
     * @date: 2025/10/15
     */
    @PostMapping("/getUsersByConditions")
    public ResponseEntity<List<User>> getUsersByConditions(@RequestBody User user) {
        if (user == null ||
                (user.getUsername() == null && user.getEmail() == null && user.getAge() == null)) {
            return ResponseEntity.badRequest().build();
        }
        List<User> users = userService.getUsersByConditions(user);
        return ResponseEntity.ok(users);
    }
}
