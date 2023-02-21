/*
 Navicat Premium Data Transfer

 Source Server         : ZYJ
 Source Server Type    : MySQL
 Source Server Version : 50719
 Source Host           : localhost:3306
 Source Schema         : app

 Target Server Type    : MySQL
 Target Server Version : 50719
 File Encoding         : 65001

 Date: 21/02/2023 18:08:22
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for consume
-- ----------------------------
DROP TABLE IF EXISTS `consume`;
CREATE TABLE `consume`  (
  `consumption` decimal(10, 3) NOT NULL COMMENT '消费金额',
  `id` int(255) UNSIGNED NOT NULL COMMENT '用户id',
  `t_time` datetime(6) NOT NULL COMMENT '消费时间',
  `sign` smallint(255) NOT NULL COMMENT '1代表消费，0代表退款',
  `deposit` decimal(10, 3) NOT NULL COMMENT '当前额度，此时还剩多少钱'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(255) UNSIGNED NOT NULL COMMENT '用户id',
  `deposit` decimal(10, 3) NULL DEFAULT NULL COMMENT '金钱额度',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
