package vip.xiaonuo.biz.modular.bidFee.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;


@Getter
@Setter
public class BizBidFeeInfo {

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




    /** *本单据已冲账金额合计（元） */
    @ApiModelProperty(value = "*本单据已冲账金额合计（元）", position = 10)
    private BigDecimal expAmount;

    /** *本单据未冲账金额合计（元） */
    @ApiModelProperty(value = "*本单据未冲账金额合计（元）", position = 11)
    private BigDecimal noExpAmount;



    /** *冲账记录 */
    @ApiModelProperty(value = "* 冲账记录", position = 12)
    private List<BizBidFeeExpInfo> expList;


}
