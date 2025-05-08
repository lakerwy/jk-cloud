package vip.xiaonuo.biz.modular.crm.subcontract.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.biz.modular.crm.subcontract.entity.SubcontractSettlementItem;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 分包合同结算审批添加参数
 *
 * @author pzh
 * @date  2025/04/30 11:19
 **/
@Getter
@Setter
public class SubcontractSettlementAddParam {

    /** 主合同ID */
    @ApiModelProperty(value = "主合同ID", position = 2)
    private String hetongId;

    /** 主合同编号 */
    @ApiModelProperty(value = "主合同编号", position = 3)
    private String contractNo;

    /** 建科所占金额 */
    @ApiModelProperty(value = "建科所占金额", position = 4)
    private BigDecimal contractAmount;

    /** 行业分类 */
    @ApiModelProperty(value = "行业分类", position = 5)
    private String industryClassification;

    /** 项目类型 */
    @ApiModelProperty(value = "项目类型", position = 6)
    private String projectType;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 7)
    private String note;

    /** 项目前期拨付情况-JSON */
    @ApiModelProperty(value = "项目前期拨付情况-JSON", position = 8)
    private String allotmentHisJson;

    /** 本次申请结算分包合同 */
    @ApiModelProperty(value = "本次申请结算分包合同", position = 10)
    private List<SubcontractSettlementItem> subcontractSettlementItemList;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 14)
    private String attachments;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 15)
    private String memo;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态", position = 17)
    private String status;

    /** SN流水号 */
    @ApiModelProperty(value = "SN流水号", position = 18)
    private String sn;

    /** 模型Id */
    @ApiModelProperty(value = "模型Id", position = 19)
    private String modelId;

    /** 流程实例Id */
    @ApiModelProperty(value = "流程实例Id", position = 20)
    private String processInstanceId;

}
