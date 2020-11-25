/*
 Navicat Premium Data Transfer

 Source Server         : 本机Mysql8.0
 Source Server Type    : MySQL
 Source Server Version : 80021
 Source Host           : localhost:3306
 Source Schema         : jike_week06_2

 Target Server Type    : MySQL
 Target Server Version : 80021
 File Encoding         : 65001

 Date: 25/11/2020 23:44:41
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_order
-- ----------------------------
DROP TABLE IF EXISTS `tb_order`;
CREATE TABLE `tb_order`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `uuid` bigint NOT NULL COMMENT '订单雪花ID',
  `user_uuid` bigint NOT NULL COMMENT '用户雪花ID',
  `user_name` bigint NOT NULL COMMENT '用户名',
  `title` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品标题',
  `description` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品描述',
  `money` int NOT NULL COMMENT '价钱/分',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '订单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tb_products
-- ----------------------------
DROP TABLE IF EXISTS `tb_products`;
CREATE TABLE `tb_products`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `uuid` bigint NOT NULL COMMENT '商品雪花ID',
  `title` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品标题',
  `description` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品描述',
  `money` int NOT NULL COMMENT '价钱/分',
  `flag` int NOT NULL COMMENT '1表示上架、0表示下架',
  `repertory` int NOT NULL COMMENT '库存',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `uuid` bigint NOT NULL COMMENT '雪花ID',
  `user_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '姓名',
  `phone` int NOT NULL COMMENT '手机号',
  `email` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
