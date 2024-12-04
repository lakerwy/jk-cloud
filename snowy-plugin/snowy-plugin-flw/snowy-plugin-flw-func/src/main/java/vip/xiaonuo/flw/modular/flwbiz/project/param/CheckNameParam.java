package vip.xiaonuo.flw.modular.flwbiz.project.param;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


@Getter
@Setter
public class CheckNameParam {
    /** 主键 - 新增|修改    页面修改时应该携带（自己修改自己特殊-不算重复） */
    @ApiModelProperty(value = "主键 - 新增|修改    页面修改时应该携带（自己修改自己特殊-不算重复）")
    private String id;

    /** 项目名称 */
    @ApiModelProperty(value = "项目名称", required = true)
    @NotBlank(message = "name项目名称不能为空")
    private String name;
}
