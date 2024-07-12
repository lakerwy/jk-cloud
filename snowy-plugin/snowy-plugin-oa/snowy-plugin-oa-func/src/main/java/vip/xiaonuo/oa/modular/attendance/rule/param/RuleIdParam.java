package vip.xiaonuo.oa.modular.attendance.rule.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 考勤规则配置表Id参数
 *
 * @author mayike
 * @date  2024/07/10 16:29
 **/
@Getter
@Setter
public class RuleIdParam {

    /** 主键 */
    @ApiModelProperty(value = "主键", required = true)
    @NotBlank(message = "id不能为空")
    private String id;
}
