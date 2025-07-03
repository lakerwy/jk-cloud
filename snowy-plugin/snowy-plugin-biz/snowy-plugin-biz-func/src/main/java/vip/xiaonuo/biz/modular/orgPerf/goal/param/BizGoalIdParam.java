package vip.xiaonuo.biz.modular.orgPerf.goal.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 子公司及生产部门绩效考核-目标登记Id参数
 *
 * @author pzh
 * @date  2025/06/24 16:33
 **/
@Getter
@Setter
public class BizGoalIdParam {

    /** 主键ID */
    @ApiModelProperty(value = "目标登记-主键ID", required = true)
    @NotBlank(message = "id不能为空")
    private String id;
}
