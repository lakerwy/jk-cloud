package vip.xiaonuo.flw.modular.relationtable.entity;

import lombok.Data;

/**
 * @PACKAGE_NAME: com.jk.jkservice.bean
 * @NAME: Field 表单字段基础类型
 * @USER: jack
 * @DATE: 5/21/2023
 * @PROJECT_NAME: jkManage
 **/
@Data
public class Field {
    /**
     * 字段描述 对应表单下-- label
     */
    String label;

    /**
     * 字段名称 对应表单下-- __vModel__
     */
    String name;

    /**
     * 字段在form 表单的类型 对应 type
     */
    String type;

    /**
     * 表字段在数据库应该存的类型  由type 来转换
     */
    String tableFieldType;

    /**
     * 是否为主键
     */
    boolean isPrimary = false;
}
