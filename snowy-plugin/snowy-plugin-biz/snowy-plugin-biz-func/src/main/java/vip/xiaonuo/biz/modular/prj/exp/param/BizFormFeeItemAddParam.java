package vip.xiaonuo.biz.modular.prj.exp.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 费用报销（二期）- 费用报销单 - 报销明细添加参数
 *
 * @author yong.ye
 * @date  2025/02/24 15:16
 **/
@Getter
@Setter
public class BizFormFeeItemAddParam {

    /** 费用报销科目（一级） */
    @ApiModelProperty(value = "费用报销科目（一级）", position = 2)
    private String expItem;

    /** 费用报销科目（二级） */
    @ApiModelProperty(value = "费用报销科目（二级）", position = 3)
    private String secondExpItem;

    /** 摘要 */
    @ApiModelProperty(value = "摘要", position = 4)
    private String descMemo;

    /** 申请金额 */
    @ApiModelProperty(value = "申请金额", position = 5)
    private BigDecimal applyAmount;

    /** 财务核定金额 */
    @ApiModelProperty(value = "财务核定金额", position = 6)
    private BigDecimal verifyAmount;

    /** 费用报销单id(BIZ_FORM_FEE.ID) */
    @ApiModelProperty(value = "费用报销单id(BIZ_FORM_FEE.ID)", position = 7)
    private String formFeeId;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 8)
    private String attach;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 9)
    private String memo;

    /** 合同id */
    @ApiModelProperty(value = "合同id", position = 20)
    private String htId;

    /** 合同名称 */
    @ApiModelProperty(value = "合同名称", position = 21)
    private String htName;

    /** 合同SN */
    @ApiModelProperty(value = "合同SN（项目合同编号）", position = 22)
    private String htSn;

    /** 进项税 */
    @ApiModelProperty(value = "进项税", position = 23)
    private BigDecimal taxAmount;

    /** 费用承担部门 */
    @ApiModelProperty(value = "费用承担部门", position = 24)
    private String deptName;

}
