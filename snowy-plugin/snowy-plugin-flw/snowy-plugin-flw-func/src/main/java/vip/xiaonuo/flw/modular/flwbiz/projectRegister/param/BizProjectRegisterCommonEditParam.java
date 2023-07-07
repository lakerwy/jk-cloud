package vip.xiaonuo.flw.modular.flwbiz.projectRegister.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class BizProjectRegisterCommonEditParam {
    /** 主键 */
    @ApiModelProperty(value = "主键", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 经营状态 */
    @ApiModelProperty(value = "经营状态", position = 16)
    private String businessStatus;
}
