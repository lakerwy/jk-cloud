/*
 Navicat Premium Data Transfer

 Source Server         : 10.0.33.82
 Source Server Type    : MySQL
 Source Server Version : 50740
 Source Host           : 10.0.33.82:3306
 Source Schema         : jk-cloud-test

 Target Server Type    : MySQL
 Target Server Version : 50740
 File Encoding         : 65001

 Date: 06/11/2023 10:51:51
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for CONFERENCE_CONFIG
-- ----------------------------
DROP TABLE IF EXISTS `CONFERENCE_CONFIG`;
CREATE TABLE `CONFERENCE_CONFIG`  (
  `ID` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
  `NAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '会议室名称',
  `LOCATION` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '会议室位置',
  `STATUS` int(10) NULL DEFAULT NULL COMMENT '会议室是否存在固定时间：0 或空为不存在，1 每周某个时间，2  每月的某个时间，3 每年的某个时间',
  `BEGIN_TIME` time(0) NULL DEFAULT NULL COMMENT '开始时间：单位-时分秒',
  `END_TIME` time(0) NULL DEFAULT NULL COMMENT '结束时间：单位-时分秒',
  `WEEK_TIME` int(10) NULL DEFAULT NULL COMMENT '对应周几：1 周一，2 周二，3 周三 ...',
  `DAY_TIME` int(10) NULL DEFAULT NULL COMMENT '对应月的哪一天',
  `CONTAIN_MAX` int(11) NULL DEFAULT NULL COMMENT '最多容纳多少人',
  `LEADER` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '负责人',
  `FILE_ID` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '附件',
  `EXT_JSON` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '扩展信息',
  `DELETE_FLAG` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除标志',
  `CREATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `CREATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建用户',
  `UPDATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `UPDATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改用户',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '会议预约-会议室数据' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
