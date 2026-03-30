package com.demo.user.entity;

import java.util.regex.Pattern;

/**
 * @FileName UserQuery
 * @Description 用户查询参数
 * @Author bytedance
 * @date 2026-03-24
 */
public class UserQuery {
    private String phone; // 手机号
    private String username; // 姓名
    private Integer minAge; // 最小年龄
    private Integer maxAge; // 最大年龄
    private Integer pageNum = 1; // 页码，默认第一页
    private Integer pageSize = 10; // 每页大小，默认10条

    // 中国大陆手机号格式正则表达式
    private static final Pattern PHONE_PATTERN = Pattern.compile("^1[3-9]\\d{9}$");

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        // 手机号格式校验
        if (phone != null && !phone.trim().isEmpty() && !PHONE_PATTERN.matcher(phone).matches()) {
            throw new IllegalArgumentException("手机号格式不正确，必须是中国大陆地区的11位手机号");
        }
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getMinAge() {
        return minAge;
    }

    public void setMinAge(Integer minAge) {
        if (minAge != null && minAge < 0) {
            throw new IllegalArgumentException("最小年龄不能为负数");
        }
        if (this.maxAge != null && minAge != null && minAge > this.maxAge) {
            throw new IllegalArgumentException("最小年龄不能大于最大年龄");
        }
        this.minAge = minAge;
    }

    public Integer getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Integer maxAge) {
        if (maxAge != null && maxAge < 0) {
            throw new IllegalArgumentException("最大年龄不能为负数");
        }
        if (this.minAge != null && maxAge != null && maxAge < this.minAge) {
            throw new IllegalArgumentException("最大年龄不能小于最小年龄");
        }
        this.maxAge = maxAge;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        if (pageNum != null && pageNum > 0) {
            this.pageNum = pageNum;
        }
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        if (pageSize != null && pageSize > 0) {
            this.pageSize = pageSize > 100 ? 100 : pageSize;
        }
    }
}