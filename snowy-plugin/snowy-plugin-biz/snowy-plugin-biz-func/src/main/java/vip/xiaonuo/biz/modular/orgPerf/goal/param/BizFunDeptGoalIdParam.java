package vip.xiaonuo.biz.modular.orgPerf.goal.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 职能部门绩效考核-目标登记Id参数
 *
 * @author pzh
 * @date  2025/06/26 16:25
 **/
@Getter
@Setter
public class BizFunDeptGoalIdParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true)
    @NotBlank(message = "id不能为空")
    private String id;
}
