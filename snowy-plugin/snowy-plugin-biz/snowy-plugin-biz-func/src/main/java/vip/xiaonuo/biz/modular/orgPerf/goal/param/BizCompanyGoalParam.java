package vip.xiaonuo.biz.modular.orgPerf.goal.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 公司绩效考核-目标登记Id参数
 *
 * @author pzh
 * @date  2025/06/18 14:24
 **/
@Getter
@Setter
public class BizCompanyGoalParam {

    /** 考核类型 */
    @ApiModelProperty(value = "考核类型", position = 1)
    private String assType;

    /** 考核周期（例如：2025） */
    @ApiModelProperty(value = "考核周期（例如：2025）", position = 2)
    private Integer assYear;

}
