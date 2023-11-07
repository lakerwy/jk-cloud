package vip.xiaonuo.biz.modular.prj.plan.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.prj.plan.entity.BizPrjPlanInvoice;

import java.math.BigDecimal;

/**
 * @author yong.ye
 * @date 2023/10/19 14:02
 */

@Getter
@Setter
public class PlanInvoiceVo extends BizPrjPlanInvoice {

    /** 计划表 - 计划付款单编号 */
    @ApiModelProperty(value = " 计划表 -计划付款单编号", position = 1)
    private String planNo;

    /** 计划表 - 合同编号 */
    @ApiModelProperty(value = " 计划表 -合同编号", position = 1)
    private String contractNo;

    /** 计划表 - 合同金额 */
    @ApiModelProperty(value = " 计划表 -合同金额", position = 1)
    private BigDecimal contractAmount;

    /** 计划表 - 合同名称 */
    @ApiModelProperty(value = " 计划表 -合同名称", position = 1)
    private String contractName;

}
