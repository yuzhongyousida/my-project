package com.demo.user.controller;

import com.demo.user.entity.User;
import com.demo.user.entity.UserQuery;
import com.demo.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * 多条件查询用户列表
     * <p>
     * 支持根据多种条件进行用户查询，并返回分页结果
     * </p>
     * 
     * @param query 查询条件对象，包含查询参数和分页信息
     * @return 包含用户列表、总记录数、当前页码和每页大小的Map
     *         <ul>
     *         <li>users: 用户列表</li>
     *         <li>total: 总记录数</li>
     *         <li>pageNum: 当前页码</li>
     *         <li>pageSize: 每页大小</li>
     *         </ul>
     * @see UserQuery
     * @see UserService#queryUsers(UserQuery)
     * @see UserService#countUsers(UserQuery)
     */
    // 多条件查询用户列表：GET /user/query
    @GetMapping("/query")
    public Map<String, Object> queryUsers(UserQuery query) {
        // 根据查询条件获取用户列表
        List<User> users = userService.queryUsers(query);
        // 获取符合条件的用户总数
        int total = userService.countUsers(query);
        // 构建返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("users", users);      // 用户列表
        result.put("total", total);      // 总记录数
        result.put("pageNum", query.getPageNum());  // 当前页码
        result.put("pageSize", query.getPageSize()); // 每页大小
        return result;
    }

}