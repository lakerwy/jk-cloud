CREATE TABLE `BIZ_TASK_GROUP`  (
                                    `ID` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
                                    `TENANT_ID` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户ID',
                                    `PARENT_ID` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '父分组ID',
                                    `GROUP_NAME` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分类名称',
                                    `GROUP_CODE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分类code',
                                    `DATA_SCOPE_ID` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '数据权限控制字段',
                                    `EXT_JSON` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '扩展信息',
                                    `DELETE_FLAG` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除标识',
                                    `CREATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
                                    `CREATE_USER` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
                                    `UPDATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
                                    `UPDATE_USER` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
                                    PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '任务来源表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;


CREATE TABLE `BIZ_TASK_PROJECT`  (
                                     `ID` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
                                     `TENANT_ID` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户ID',
                                     `PRO_NAME` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '项目名称',
                                     `PRO_DESC` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '项目简介',
                                     `PRO_SOURCE_ID` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '项目源主键ID',
                                     `PRO_SOURCE_TABLE` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '项目源表名称',
                                     `GROUP_ID` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '项目分组ID',
                                     `PRO_RIGHT` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '项目权限',
                                     `DATA_SCOPE_ID` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '数据权限控制字段',
                                     `EXT_JSON` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '扩展信息',
                                     `DELETE_FLAG` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除标识',
                                     `CREATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
                                     `CREATE_USER` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
                                     `UPDATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
                                     `UPDATE_USER` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
                                     PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '任务项目表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE `BIZ_TASK_GROUP_RES`  (
                                       `ID` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
                                       `TENANT_ID` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户ID',
                                       `TASK_GROUP_ID` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '任务来源ID',
                                       `OBJECT_ID` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '对象ID',
                                       `OBJECT_TYPE` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '对象类型（人或部门）',
                                       `DATA_SCOPE_ID` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '数据权限控制字段',
                                       `EXT_JSON` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '扩展信息',
                                       `DELETE_FLAG` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除标识',
                                       `CREATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
                                       `CREATE_USER` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
                                       `UPDATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
                                       `UPDATE_USER` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
                                       PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '任务分组关联表（人或部门）' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE `BIZ_TASK_INFO`  (
                                  `ID` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
                                  `TENANT_ID` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户ID',
                                  `PARENT_ID` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '父任务ID',
                                  `TASK_NAME` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '任务名称',
                                  `TASK_REMARK` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '项目备注',
                                  `PRO_ID` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '项目ID',
                                  `TASK_START_TIME` datetime(0) NULL DEFAULT NULL COMMENT '任务开始时间',
                                  `TASK_END_TIME` datetime(0) NULL DEFAULT NULL COMMENT '任务结束时间',
                                  `BELONGER` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '任务执行者',
                                  `TASK_STATUS` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '任务状态',
                                  `TASK_PRIORITY` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '任务优先级',
                                  `TASK_PARTICIPANTS` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参与人集合',
                                  `TASK_MODEL` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '任务模式（公开或私密）',
                                  `DATA_SCOPE_ID` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '数据权限控制字段',
                                  `EXT_JSON` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '扩展信息',
                                  `DELETE_FLAG` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除标识',
                                  `CREATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
                                  `CREATE_USER` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
                                  `UPDATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
                                  `UPDATE_USER` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
                                  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '任务主表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;