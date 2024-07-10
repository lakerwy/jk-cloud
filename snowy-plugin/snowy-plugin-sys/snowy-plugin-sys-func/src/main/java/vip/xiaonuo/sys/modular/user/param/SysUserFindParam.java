package vip.xiaonuo.sys.modular.user.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/7/10 14:33
 */
@Getter
@Setter
public class SysUserFindParam {

    /** 账号 */
    @ApiModelProperty(value = "字段名称", required = true, position = 1)
    @NotBlank(message = "字段名称不能为空")
    private String fieldName;

    /** 账号 */
    @ApiModelProperty(value = "字段值", required = true, position = 1)
    @NotBlank(message = "字段值不能为空")
    private String fieldValue;
}
