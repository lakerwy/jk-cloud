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

 Date: 30/10/2023 11:21:12
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for HR_APPLY_WORK_OT
-- ----------------------------
DROP TABLE IF EXISTS `HR_APPLY_WORK_OT`;
CREATE TABLE `HR_APPLY_WORK_OT`  (
  `ID` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
  `TENANT_ID` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户ID',
  `APPLY_USER_ID` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '申请人ID',
  `APPLY_USER_NAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '申请人名称',
  `APPLY_BEGIN_TIME` datetime(0) NULL DEFAULT NULL COMMENT '申请开始时间',
  `APPLY_END_TIME` datetime(0) NULL DEFAULT NULL COMMENT '申请结束时间',
  `APPLY_WORK_REASON` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '申请理由',
  `WORK_HOURS` double(10, 0) NULL DEFAULT NULL COMMENT '加班时长单位：小时',
  `PROJECT_ID` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '项目编号',
  `PROJECT_NAME` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '项目名称',
  `MODEL_ID` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模型ID',
  `PROCESS_INSTANCE_ID` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '流程实例id',
  `SN` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'SN单号',
  `STATUS` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '流程状态',
  `EXT_JSON` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '扩展信息',
  `DELETE_FLAG` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除标识',
  `CREATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `CREATE_USER` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `UPDATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `UPDATE_USER` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '考勤-申请加班数据表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
