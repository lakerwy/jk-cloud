package vip.xiaonuo.biz.modular.task.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author yong.ye
 * @date 2023/8/8 8:38
 */

@Getter
@Setter
public class BizTaskUpdateParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 更新字段名 */
    @ApiModelProperty(value = "更新字段名", position = 2)
    private String colName;

    /** 更新字段值 */
    @ApiModelProperty(value = "更新字段值", position = 3)
    private String colValue;
}
