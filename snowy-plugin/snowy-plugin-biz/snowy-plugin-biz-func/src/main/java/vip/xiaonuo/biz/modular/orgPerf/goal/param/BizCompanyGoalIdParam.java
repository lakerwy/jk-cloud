package vip.xiaonuo.biz.modular.orgPerf.goal.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 公司绩效考核-目标登记Id参数
 *
 * @author pzh
 * @date  2025/06/18 10:02
 **/
@Getter
@Setter
public class BizCompanyGoalIdParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true)
    @NotBlank(message = "id不能为空")
    private String id;
}
