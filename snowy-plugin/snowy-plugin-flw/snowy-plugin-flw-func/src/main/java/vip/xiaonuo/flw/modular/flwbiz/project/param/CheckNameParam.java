package vip.xiaonuo.flw.modular.flwbiz.project.param;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


@Getter
@Setter
public class CheckNameParam {
    /** 主键 */
    @ApiModelProperty(value = "主键", required = true)
    @NotBlank(message = "id主键不能为空")
    private String id;

    /** 项目名称 */
    @ApiModelProperty(value = "项目名称", required = true)
    @NotBlank(message = "name项目名称不能为空")
    private String name;
}
