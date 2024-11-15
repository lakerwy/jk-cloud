package vip.xiaonuo.flw.modular.relationtable.param;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * nodejs - 流程发起-业务表单关联关系
 */

@Getter
@Setter
public class FormRelationTableNodeJsParam {

    /** 流程实例Id */
    @ApiModelProperty(value = "流程实例Id",required = true, position = 1)
    @NotBlank(message = "processInstanceId不能为空")
    private String processInstanceId;

    /** 数据库类别：MASTER 主库  slave_1 业务库 */
    @ApiModelProperty(value = "数据库类别：MASTER 主库  slave_1 业务库",required = true, position = 2)
    @NotBlank(message = "dbType不能为空")
    private String dbType;

    /** 数据库表名：如 BIZ_PROJECT */
    @ApiModelProperty(value = "数据库表名：如 BIZ_PROJECT",required = true, position = 3)
    @NotBlank(message = "dbTable不能为空")
    private String dbTable;
}
