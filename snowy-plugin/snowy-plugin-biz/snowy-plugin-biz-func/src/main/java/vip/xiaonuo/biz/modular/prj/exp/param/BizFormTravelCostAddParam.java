package vip.xiaonuo.biz.modular.prj.exp.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 费用报销（二期）- 差旅费用报销单 - 成本明细添加参数
 *
 * @author yong.ye
 * @date  2025/02/24 15:19
 **/
@Getter
@Setter
public class BizFormTravelCostAddParam {

    /** 合同id */
    @ApiModelProperty(value = "合同id", position = 2)
    private String htId;

    /** 合同名称 */
    @ApiModelProperty(value = "合同名称", position = 3)
    private String htName;

    /** 合同SN */
    @ApiModelProperty(value = "合同SN", position = 4)
    private String htSn;

    /** 费用类型（一级） */
    @ApiModelProperty(value = "费用类型（一级）", position = 5)
    private String expItem;

    /** 费用类型（二级） */
    @ApiModelProperty(value = "费用类型（二级）", position = 6)
    private String secondExpItem;

    /** 摘要 */
    @ApiModelProperty(value = "摘要", position = 7)
    private String desc;

    /** 申请金额 */
    @ApiModelProperty(value = "申请金额", position = 8)
    private BigDecimal applyAmount;

    /** 财务核定金额 */
    @ApiModelProperty(value = "财务核定金额", position = 9)
    private BigDecimal verifyAmount;

    /** 进项税 */
    @ApiModelProperty(value = "进项税", position = 10)
    private BigDecimal taxAmount;

    /** 费用承担部门 */
    @ApiModelProperty(value = "费用承担部门", position = 11)
    private String deptName;

    /** 差旅费用报销单id(BIZ_FORM_TRAVEL.ID) */
    @ApiModelProperty(value = "差旅费用报销单id(BIZ_FORM_TRAVEL.ID)", position = 12)
    private String formTravelId;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 13)
    private String attach;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 14)
    private String memo;

}
