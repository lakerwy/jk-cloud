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

 Date: 19/10/2023 09:02:16
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for HR_USER_ATTENDANCE_CONFIG
-- ----------------------------
DROP TABLE IF EXISTS `HR_USER_ATTENDANCE_CONFIG`;
CREATE TABLE `HR_USER_ATTENDANCE_CONFIG`  (
  `ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键id',
  `TENANT_ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户id',
  `ORDER_NUMBER` int(10) NULL DEFAULT NULL COMMENT '排班顺序',
  `ATTENDANCE_TYPE_ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '考勤班次组ID',
  `ATTENDANCE_TYPE_NAME` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '考勤班次类别名，比如标准考勤，移动考勤等',
  `DUTY_TYPE` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '上班OnDuty，下班 OffDuty ，休息 REST',
  `BEGIN_MIN_TIME` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '允许最早多少时间打卡',
  `CHECK_TIME` time(6) NULL DEFAULT NULL COMMENT '打卡时间点',
  `END_MIN_TIME` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '允许最晚多少时间打卡',
  `ACTION_RANGE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '作用范围区域 ',
  `IS_VALID_RANGE` int(5) NULL DEFAULT NULL COMMENT '区域范围是否有效',
  `WORK_HOURS` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '班次工时，单位：小时',
  `EXT_JSON` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '扩展信息',
  `DELETE_FLAG` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除标志',
  `CREATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '创建时间 ',
  `CREATE_USER` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建用户',
  `UPDATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `UPDATE_USER` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改用户',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '打卡班次配置表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
