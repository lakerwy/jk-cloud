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

 Date: 19/10/2023 09:01:55
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for HR_USER_APPLY_ATTENDANCE
-- ----------------------------
DROP TABLE IF EXISTS `HR_USER_APPLY_ATTENDANCE`;
CREATE TABLE `HR_USER_APPLY_ATTENDANCE`  (
  `ID` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
  `TENANT_ID` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户id',
  `APPLY_USER_ID` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '申请用户ID',
  `APPLY_TYPE` int(10) NULL DEFAULT NULL COMMENT '申请类型：是否加班考勤  1 ，是否弹性   2，是否异地   3，是否免打卡  4，申请补卡 5',
  `APPLY_ATTENDANCE_TIME` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '申请时间',
  `APPLY_BEGIN_TIME` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '申请开始时间',
  `APPLY_ENG_TIME` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '申请结束时间',
  `RESUBMIT_ATTENDANCE_BEGIN_TIME` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '补卡申请开始时间',
  `RESUBMIT_ATTENDANCE_END_TIME` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '补卡申请结束时间',
  `MODEL_ID` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模型ID',
  `PROCESS_INSTANCE_ID` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '流程实例id',
  `SN` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'SN单号',
  `STATUS` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '流程状态',
  `EXT_JSON` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '扩展信息',
  `DELETE_FLAG` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除标志',
  `CREATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `CREATE_USER` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建用户',
  `UPDATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `UPDATE_USER` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改用户',
  `APPLY_REASON` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '理由',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '补卡或远程打卡申请' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
