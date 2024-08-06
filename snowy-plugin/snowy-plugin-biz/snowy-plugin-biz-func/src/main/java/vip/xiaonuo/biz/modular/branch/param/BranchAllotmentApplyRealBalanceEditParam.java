package vip.xiaonuo.biz.modular.branch.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

/**
 * 分公司日常费用拨付 - 申请流程编辑参数
 *
 * @author pzh
 * @date  2024/08/06 14:03
 **/
@Getter
@Setter
public class BranchAllotmentApplyRealBalanceEditParam {

    /** 主键ID */
    @ApiModelProperty(value = "分公司日常费用拨付-主键ID", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 流程实例Id */
    @ApiModelProperty(value = "流程实例Id", position = 18)
    private String processInstanceId;

    /** 出纳登记账户余额 */
    @ApiModelProperty(value = "出纳登记账户余额", position = 9)
    private BigDecimal realBalance;

}
