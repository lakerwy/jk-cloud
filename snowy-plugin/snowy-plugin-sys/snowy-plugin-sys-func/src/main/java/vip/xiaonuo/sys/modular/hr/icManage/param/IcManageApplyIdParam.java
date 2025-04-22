package vip.xiaonuo.sys.modular.hr.icManage.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 工商信息新增|变更|注销--流程申请Id参数
 *
 * @author yy
 * @date  2025/04/21 15:58
 **/
@Getter
@Setter
public class IcManageApplyIdParam {

    /** 主键 */
    @ApiModelProperty(value = "主键", required = true)
    @NotBlank(message = "id不能为空")
    private String id;
}
