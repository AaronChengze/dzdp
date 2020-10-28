/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80019
 Source Host           : localhost:3306
 Source Schema         : dianping

 Target Server Type    : MySQL
 Target Server Version : 80019
 File Encoding         : 65001

 Date: 28/10/2020 13:53:29
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user_test
-- ----------------------------
DROP TABLE IF EXISTS `user_test`;
CREATE TABLE `user_test` (
  `id` int DEFAULT NULL COMMENT 'id',
  `name` varchar(64) DEFAULT NULL COMMENT '用户名',
  `password` varchar(64) DEFAULT NULL COMMENT '密码',
  `age` int DEFAULT NULL COMMENT '年龄'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_test
-- ----------------------------
BEGIN;
INSERT INTO `user_test` VALUES (12, 'cassie', '123456', 25);
INSERT INTO `user_test` VALUES (11, 'zhangs', '1234562', 26);
INSERT INTO `user_test` VALUES (23, 'zhangs', '2321312', 27);
INSERT INTO `user_test` VALUES (22, 'tom', 'asdfg', 28);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
