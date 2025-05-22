package vip.xiaonuo.biz.modular.prj.exp.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class BizBidFeeVo {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** *支付金额（元） */
    @ApiModelProperty(value = "*支付金额（元）", position = 2)
    private BigDecimal payAmount;

    /** SN流水号 */
    @ApiModelProperty(value = "SN流水号", position = 3)
    private String sn;

    /** 流程实例Id */
    @ApiModelProperty(value = "流程实例Id", position = 4)
    private String processInstanceId;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态", position = 5)
    private String status;





    /** *投标费用金额（元） = 支付金额（元） */
    @ApiModelProperty(value = "*投标费用金额（元）", position = 87)
    private BigDecimal advanceAmount;

    /** 冲账余额 (投标费用金额 - （审批通过)费用报销、差旅报销）*/
    @ApiModelProperty(value = "冲账余额 (投标费用金额 - （审批通过)费用报销、差旅报销）", position = 88)
    private BigDecimal cashBalance;

    /** 冲账金额（审批通过合计） */
    @ApiModelProperty(value = "冲账金额（审批通过合计）", position = 89)
    private BigDecimal backAmount;

    /** 未冲账金额 （投标费用金额-冲账金额）*/
    @ApiModelProperty(value = "未冲账金额 （投标费用金额-冲账金额）", position = 90)
    private BigDecimal noBackAmount;
}
