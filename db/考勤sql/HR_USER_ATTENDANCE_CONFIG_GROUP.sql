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

 Date: 19/10/2023 09:02:21
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for HR_USER_ATTENDANCE_CONFIG_GROUP
-- ----------------------------
DROP TABLE IF EXISTS `HR_USER_ATTENDANCE_CONFIG_GROUP`;
CREATE TABLE `HR_USER_ATTENDANCE_CONFIG_GROUP`  (
  `ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
  `ATTENDANCE_TYPE_NAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '班次名称',
  `LEAD_ID` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '负责人ID',
  `LEAD_NAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '负责人名称',
  `ATTENDANCE_USER_NUM` int(20) NULL DEFAULT NULL COMMENT '班次应用人数',
  `IS_DEFAULT_CONFIG` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否默认考勤配置',
  `EXT_JSON` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '额外信息',
  `DELETE_FLAG` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除标识',
  `CREATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `CREATE_USER` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `UPDATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `UPDATE_USER` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '考勤班次组配置表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
