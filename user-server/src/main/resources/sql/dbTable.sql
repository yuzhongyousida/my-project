-- 创建数据库
CREATE DATABASE IF NOT EXISTS demo_db DEFAULT CHARACTER SET utf8mb4;
USE demo_db;

-- 创建user表（用户模块）
CREATE TABLE IF NOT EXISTS `user` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `username` VARCHAR(50) NOT NULL COMMENT '用户名',
    `phone` VARCHAR(20) DEFAULT NULL COMMENT '手机号',
    `age` INT(11) DEFAULT NULL COMMENT '年龄',
    `create_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 创建merchant表（商家模块）
CREATE TABLE IF NOT EXISTS `merchant` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `name` VARCHAR(100) NOT NULL COMMENT '商家名称',
    `phone` VARCHAR(20) DEFAULT NULL COMMENT '联系电话',
    `address` VARCHAR(200) DEFAULT NULL COMMENT '地址',
    `description` VARCHAR(500) DEFAULT NULL COMMENT '描述',
    `is_deleted` TINYINT(1) DEFAULT 0 COMMENT '逻辑删除标记：0-未删除，1-已删除',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商家表';

-- 为merchant表添加is_deleted字段（如果表已存在）
ALTER TABLE merchant ADD COLUMN IF NOT EXISTS is_deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除标记：0-未删除，1-已删除';

-- 创建product表（商品模块）
CREATE TABLE IF NOT EXISTS `product` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `merchant_id` BIGINT(20) DEFAULT NULL COMMENT '商家ID',
    `name` VARCHAR(100) NOT NULL COMMENT '商品名称',
    `price` DECIMAL(10,2) NOT NULL COMMENT '商品价格',
    `stock` INT(11) DEFAULT 0 COMMENT '库存',
    `is_deleted` TINYINT(1) DEFAULT 0 COMMENT '逻辑删除标记：0-未删除，1-已删除',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品表';

-- 为product表添加is_deleted字段（如果表已存在）
ALTER TABLE product ADD COLUMN IF NOT EXISTS is_deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除标记：0-未删除，1-已删除';

-- 2026-03-24，新增根据动态条件查询用户信息功能：添加create_time字段
ALTER TABLE demo_db.user ADD COLUMN IF NOT EXISTS create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间';