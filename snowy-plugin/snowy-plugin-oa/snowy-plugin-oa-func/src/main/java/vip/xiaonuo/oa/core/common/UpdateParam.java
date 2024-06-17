package vip.xiaonuo.oa.core.common;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/6/11 15:30
 * 通用更新字段
 */
public class UpdateParam {
    @ApiModelProperty(value = "主键ID", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 更新字段名 */
    @ApiModelProperty(value = "更新字段名", position = 2)
    private String colName;

    /** 更新字段值 */
    @ApiModelProperty(value = "更新字段值", position = 3)
    private Object colValue;
}
