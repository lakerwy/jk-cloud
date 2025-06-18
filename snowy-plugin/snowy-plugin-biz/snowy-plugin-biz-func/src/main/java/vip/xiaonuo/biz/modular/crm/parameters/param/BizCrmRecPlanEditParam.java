package vip.xiaonuo.biz.modular.crm.parameters.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 合同收款计划编辑参数
 *
 * @author pzh
 * @date  2025/05/30 09:35
 **/
@Getter
@Setter
public class BizCrmRecPlanEditParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true, position = 1)
    private String id;

    /** 关联-合同参数主键ID */
    @ApiModelProperty(value = "关联-合同参数主键ID", position = 2)
    private String parametersId;

    @ApiModelProperty(value = "合同ID", position = 3)
    private String hetongId;

    /** 款项性质 */
    @ApiModelProperty(value = "款项性质", position = 4)
    private String feeType;

    /** 收款节点 */
    @ApiModelProperty(value = "收款节点", position = 5)
    private String recNode;

    /** 计划收款日期（格式：yyyy-MM-dd） */
    @ApiModelProperty(value = "计划收款日期（格式：yyyy-MM-dd）", position = 6)
    private Date plannedRecDate;

    /** 缓冲期 */
    @ApiModelProperty(value = "缓冲期", position = 7)
    private String gracePeriod;

    /** 收款比例（按总价计算） */
    @ApiModelProperty(value = "收款比例（按总价计算）", position = 8)
    private BigDecimal recRatio;

    /** 计划收款金额 */
    @ApiModelProperty(value = "计划收款金额", position = 9)
    private BigDecimal plannedRecAmount;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 10)
    private String memo;

    /** 回款情况 */
    @ApiModelProperty(value = "回款情况", position = 11)
    private String recStatus;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 15)
    private String attach;

}
