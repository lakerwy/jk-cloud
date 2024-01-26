package vip.xiaonuo.sys.modular.user.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.sys.modular.user.result.SystemEpcResult;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class SystemEpcParam {
    /** 主键 */
    @ApiModelProperty(value = "系统主键id", required = true)
    @NotBlank(message = "系统主键id不能为空")
    private String id;

    @ApiModelProperty(value = "建科用户信息", required = true)
    @NotNull(message = "建科用户信息不能为空")
    private List<SystemEpcResult> systemEpcResultList;
}
