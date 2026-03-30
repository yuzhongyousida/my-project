-- 创建数据库
CREATE DATABASE IF NOT EXISTS demo_db DEFAULT CHARACTER SET utf8mb4;
USE demo_db;

-- 创建user表（用户模块）
CREATE TABLE IF NOT EXISTS `user` (
                                      `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `username` VARCHAR(50) NOT NULL COMMENT '用户名',
    `phone` VARCHAR(20) DEFAULT NULL COMMENT '手机号',
    `age` INT(11) DEFAULT NULL COMMENT '年龄',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

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

-- 为product表添加merchant_id字段（如果表已存在）
ALTER TABLE product ADD COLUMN IF NOT EXISTS merchant_id BIGINT(20) DEFAULT NULL COMMENT '商家ID';

-- 为product表添加is_deleted字段（如果表已存在）
ALTER TABLE product ADD COLUMN IF NOT EXISTS is_deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除标记：0-未删除，1-已删除';