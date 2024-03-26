package vip.xiaonuo.sys.modular.hr.salary.param.customer;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 更新字段参数
 */

@Getter
@Setter
public class UpdateFieldParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 待更新字段名称：如 isSlow 是否缓发 */
    @ApiModelProperty(value = "待更新字段名称：如 isSlow 是否缓发", position = 2)
    private String fieldName;

    /** 待更新字段值：如 isSlow 是 | 否 */
    @ApiModelProperty(value = "待更新字段值：如 isSlow 是 | 否", position = 3)
    private String fieldValue;

}
