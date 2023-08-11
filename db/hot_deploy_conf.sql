CREATE TABLE `HOT_DEPLOY_CONF` (
                                   `ID` varchar(255) NOT NULL COMMENT '主键ID',
                                   `HASHVALUE` varchar(255) DEFAULT NULL COMMENT '包名的hash值',
                                   `VERSION` varchar(255) DEFAULT NULL COMMENT '版本号',
                                   `PACKAGE_URL` varchar(255) DEFAULT NULL COMMENT '包路径',
                                   `DELETE_FLAG` varchar(20) DEFAULT NULL COMMENT '删除标志',
                                   `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
                                   `CREATE_USER` varchar(20) DEFAULT NULL COMMENT '创建用户',
                                   `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
                                   `UPDATE_USER` varchar(20) DEFAULT NULL COMMENT '修改用户',
                                   PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;