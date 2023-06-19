package vip.xiaonuo.flw.modular.relationtable.entity;

import lombok.Data;

import java.util.List;

/**
 * @PACKAGE_NAME: com.jk.jkservice
 * @NAME: DbTable  数据库表
 * @USER: jack
 * @DATE: 5/21/2023
 * @PROJECT_NAME: jkManage
 **/
@Data
public class DbTable {

    /**
     * 表名称
     */
    private String tableName;

    /***
     * 表描述
     */
    private String description;

    /**
     * 字段集合
     */
    private List<Field> fields;

    /**
     * 主键字段
     */
    private Field field;
}
