CREATE TABLE `PAN_COMMON_FILE` (
                                   `ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                                   `TENANT_ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户id',
                                   `USER_FILE_ID` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户文件id',
                                   `EXT_JSON` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '扩展信息',
                                   `DELETE_FLAG` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除标志',
                                   `CREATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
                                   `CREATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建用户',
                                   `UPDATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
                                   `UPDATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改用户',
                                   PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '共享文件表' ROW_FORMAT = DYNAMIC;

CREATE TABLE `PAN_FILE` (
                            `ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                            `FILE_SIZE` bigint DEFAULT NULL COMMENT '文件大小',
                            `SUFFIX` varchar(255) DEFAULT NULL COMMENT '文件后缀',
                            `FILE_STATUS` int DEFAULT NULL COMMENT '文件状态(0-失效，1-生效)',
                            `FILE_URL` varchar(500) DEFAULT NULL COMMENT '文件url',
                            `IDENTIFIER` varchar(32) DEFAULT NULL COMMENT 'md5唯一标识',
                            `STORAGE_TYPE` varchar(20) DEFAULT NULL COMMENT '存储类型',
                            `EXT_JSON` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '扩展信息',
                            `DELETE_FLAG` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除标志',
                            `CREATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
                            `CREATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建用户',
                            `UPDATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
                            `UPDATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改用户',
                            PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '文件主表' ROW_FORMAT = DYNAMIC;

CREATE TABLE `PAN_FILE_TYPE` (
                                 `ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                                 `TENANT_ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户id',
                                 `FILE_TYPE_NAME` varchar(50) DEFAULT NULL COMMENT '文件类型名',
                                 `ORDER_NUM` int DEFAULT NULL COMMENT '次序',
                                 `EXT_JSON` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '扩展信息',
                                 `DELETE_FLAG` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除标志',
                                 `CREATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
                                 `CREATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建用户',
                                 `UPDATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
                                 `UPDATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改用户',
                                 PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '文件类型' ROW_FORMAT = DYNAMIC;

CREATE TABLE `PAN_FILE_CLASSIFICATION` (
                                           `ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                                           `TENANT_ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户id',
                                           `FILE_EXTEND_NAME` varchar(50) DEFAULT NULL COMMENT '文件扩展名',
                                           `FILE_TYPE_ID` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件类型ID',
                                           `EXT_JSON` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '扩展信息',
                                           `DELETE_FLAG` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除标志',
                                           `CREATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
                                           `CREATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建用户',
                                           `UPDATE_TIME` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
                                           `UPDATE_USER` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改用户',
                                           PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '文件类型拓展表' ROW_FORMAT = DYNAMIC;

CREATE TABLE `PAN_FILE_STORAGE` (
                                    `ID` VARCHAR ( 20 ) CHARACTER
                                        SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                                    `TENANT_ID` VARCHAR ( 20 ) CHARACTER
                                        SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户id',
                                    `STORAGE_SIZE` BIGINT(20) DEFAULT NULL COMMENT '占用存储大小',
                                    `TOTAL_STORAGE_SIZE` BIGINT(20) DEFAULT NULL COMMENT '总存储大小',
                                    `EXT_JSON` LONGTEXT CHARACTER
                                        SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '扩展信息',
                                    `DELETE_FLAG` VARCHAR ( 255 ) CHARACTER
                                        SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除标志',
                                    `CREATE_TIME` datetime ( 0 ) NULL DEFAULT NULL COMMENT '创建时间',
                                    `CREATE_USER` VARCHAR ( 20 ) CHARACTER
                                        SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建用户',
                                    `UPDATE_TIME` datetime ( 0 ) NULL DEFAULT NULL COMMENT '修改时间',
                                    `UPDATE_USER` VARCHAR ( 20 ) CHARACTER
                                        SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改用户',
                                    PRIMARY KEY ( `ID` ) USING BTREE
) ENGINE = INNODB CHARACTER
SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '存储实体类' ROW_FORMAT = DYNAMIC;

CREATE TABLE `PAN_MUSIC` (
                             `ID` VARCHAR ( 20 ) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                             `TENANT_ID` VARCHAR ( 20 ) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户id',
                             `ALBUM` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
                             `ALBUM_ARTIST` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
                             `ALBUM_IMAGE` mediumblob,
                             `ARTIST` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
                             `COMMENT` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
                             `COMPOSER` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
                             `COPYRIGHT` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
                             `ENCODER` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
                             `FILE_ID` bigint(20) DEFAULT NULL COMMENT '文件id',
                             `GENRE` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
                             `LYRICS` varchar(10000) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '歌词',
                             `ORIGINAL_ARTIST` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
                             `PUBLICER` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
                             `TITLE` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
                             `TRACK` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
                             `TRACK_LENGTH` float DEFAULT NULL,
                             `URL` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
                             `YEAR` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
                             `EXT_JSON` LONGTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '扩展信息',
                             `DELETE_FLAG` VARCHAR ( 255 ) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除标志',
                             `CREATE_TIME` datetime ( 0 ) NULL DEFAULT NULL COMMENT '创建时间',
                             `CREATE_USER` VARCHAR ( 20 ) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建用户',
                             `UPDATE_TIME` datetime ( 0 ) NULL DEFAULT NULL COMMENT '修改时间',
                             `UPDATE_USER` VARCHAR ( 20 ) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改用户',
                             PRIMARY KEY ( `ID` ) USING BTREE
) ENGINE = INNODB CHARACTER
SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '云盘-音乐实体类' ROW_FORMAT = DYNAMIC;

CREATE TABLE `PAN_PERMISSION` (
                                  `ID` VARCHAR ( 20 ) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                                  `TENANT_ID` VARCHAR ( 20 ) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户id',
                                  `ORDER_NUM` int(2) DEFAULT NULL COMMENT '次序',
                                  `PARENT_ID` bigint(20) DEFAULT NULL COMMENT '父编号',
                                  `PERMISSION_CODE` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '权限标识码',
                                  `PERMISSION_NAME` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '权限名称',
                                  `RESOURCE_TYPE` int(2) DEFAULT NULL COMMENT '资源类型',
                                  `EXT_JSON` LONGTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '扩展信息',
                                  `DELETE_FLAG` VARCHAR ( 255 ) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除标志',
                                  `CREATE_TIME` datetime ( 0 ) NULL DEFAULT NULL COMMENT '创建时间',
                                  `CREATE_USER` VARCHAR ( 20 ) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建用户',
                                  `UPDATE_TIME` datetime ( 0 ) NULL DEFAULT NULL COMMENT '修改时间',
                                  `UPDATE_USER` VARCHAR ( 20 ) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改用户',
                                  PRIMARY KEY ( `ID` ) USING BTREE
) ENGINE = INNODB CHARACTER
SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '云盘-图片实体表' ROW_FORMAT = DYNAMIC;


CREATE TABLE `PAN_PICTURE_FILE` (
                                    `ID` VARCHAR ( 20 ) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                                    `TENANT_ID` VARCHAR ( 20 ) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户id',
                                    `EXTEND_NAME` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '扩展名',
                                    `FILE_NAME` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '文件名',
                                    `FILE_SIZE` bigint(10) DEFAULT NULL COMMENT '文件大小',
                                    `FILE_URL` varchar(500) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '文件url',
                                    `STORAGE_TYPE` int(1) DEFAULT NULL COMMENT '存储类型',
                                    `USER_ID` bigint(20) DEFAULT NULL COMMENT '用户id',
                                    `EXT_JSON` LONGTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '扩展信息',
                                    `DELETE_FLAG` VARCHAR ( 255 ) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除标志',
                                    `CREATE_TIME` datetime ( 0 ) NULL DEFAULT NULL COMMENT '创建时间',
                                    `CREATE_USER` VARCHAR ( 20 ) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建用户',
                                    `UPDATE_TIME` datetime ( 0 ) NULL DEFAULT NULL COMMENT '修改时间',
                                    `UPDATE_USER` VARCHAR ( 20 ) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改用户',
                                    PRIMARY KEY ( `ID` ) USING BTREE
) ENGINE = INNODB CHARACTER
SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '云盘-图片文件' ROW_FORMAT = DYNAMIC;



CREATE TABLE `PAN_UPLOAD_TASK` (
                                   `ID` VARCHAR ( 20 ) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                                   `TENANT_ID` VARCHAR ( 20 ) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户id',
                                   `EXTEND_NAME` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '扩展名',
                                   `FILE_NAME` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '文件名',
                                   `FILE_PATH` varchar(500) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '文件路径',
                                   `IDENTIFER` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'md5唯一标识',
                                   `UPLOAD_STATUS` int(1) DEFAULT NULL COMMENT '上传状态(1-成功,0-失败或未完成)',
                                   `UPLOAD_TIME` datetime ( 0 ) NULL DEFAULT NULL COMMENT '上传时间',
                                   `USER_ID` bigint(20) DEFAULT NULL COMMENT '用户id',
                                   `EXT_JSON` LONGTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '扩展信息',
                                   `DELETE_FLAG` VARCHAR ( 255 ) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除标志',
                                   `CREATE_TIME` datetime ( 0 ) NULL DEFAULT NULL COMMENT '创建时间',
                                   `CREATE_USER` VARCHAR ( 20 ) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建用户',
                                   `UPDATE_TIME` datetime ( 0 ) NULL DEFAULT NULL COMMENT '修改时间',
                                   `UPDATE_USER` VARCHAR ( 20 ) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改用户',
                                   PRIMARY KEY ( `ID` ) USING BTREE
) ENGINE = INNODB CHARACTER
SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '云盘-上传任务主表' ROW_FORMAT = DYNAMIC;
