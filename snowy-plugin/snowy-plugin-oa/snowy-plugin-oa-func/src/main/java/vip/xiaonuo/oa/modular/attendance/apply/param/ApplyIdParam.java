package vip.xiaonuo.oa.modular.attendance.apply.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 考勤申请管理表Id参数
 *
 * @author mayike
 * @date  2024/07/15 09:56
 **/
@Getter
@Setter
public class ApplyIdParam {

    /** 主键 */
    @ApiModelProperty(value = "主键", required = true)
    @NotBlank(message = "id不能为空")
    private String id;
}
