package vip.xiaonuo.sys.modular.hr.icManage.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 工商管理Id参数
 *
 * @author pzh
 * @date  2025/02/25 16:33
 **/
@Getter
@Setter
public class IcManageIdParam {

    /** 主键 */
    @ApiModelProperty(value = "主键", required = true)
    @NotBlank(message = "id不能为空")
    private String id;
}
