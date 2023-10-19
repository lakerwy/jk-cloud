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

 Date: 19/10/2023 09:02:09
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for HR_USER_ATTENDANCE
-- ----------------------------
DROP TABLE IF EXISTS `HR_USER_ATTENDANCE`;
CREATE TABLE `HR_USER_ATTENDANCE`  (
  `ID` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
  `USER_ID` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '人员信息ID',
  `TENANT_ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户id',
  `CHECK_TIME` datetime(0) NULL DEFAULT NULL COMMENT '打卡时间',
  `MACHINE_CODE` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '打卡点位(机位)',
  `ABNORMAL_FLAG` int(5) NULL DEFAULT NULL COMMENT '打卡是否异常：2 异常，1 正常',
  `CHECK_TYPE` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '上下班标识；OnDuty：上班；OffDuty：下班',
  `CONFIG_ORDER_NUMBER` int(10) NULL DEFAULT NULL COMMENT '考勤班次排序号',
  `LATITUDE` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '打卡地址对应的纬度',
  `LONGITUDE` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '打卡地址对应的经度',
  `IS_RESUBMIT_ATTENDANCE_FLAG` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否补卡标识',
  `APPLY_RESUBMIT_ATTENDANCE_ID` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '申请补卡ID',
  `EXT_JSON` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '扩展信息',
  `DELETE_FLAG` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除标志',
  `CREATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `CREATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建用户',
  `UPDATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '修改时间 ',
  `UPDATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改用户',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '打卡记录表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
