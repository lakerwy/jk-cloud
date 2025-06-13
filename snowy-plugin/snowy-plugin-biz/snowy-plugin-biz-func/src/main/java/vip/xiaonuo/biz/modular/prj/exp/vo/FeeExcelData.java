package vip.xiaonuo.biz.modular.prj.exp.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import vip.xiaonuo.biz.modular.prj.exp.param.BizFormFeeCostEditParam;
import vip.xiaonuo.biz.modular.prj.exp.param.BizFormFeeItemEditParam;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Getter
@Setter
public class FeeExcelData {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 公司 */
    @ApiModelProperty(value = "公司", position = 2)
    private String company;

    /** 部门Id */
    @ApiModelProperty(value = "部门Id", position = 3)
    private String orgId;

    /** 部门 */
    @ApiModelProperty(value = "部门", position = 4)
    private String orgName;

    /** 日期 */
    @ApiModelProperty(value = "日期", position = 5)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date expDate;

    /** 日期(字符串格式 2025年1月2日) */
    @ApiModelProperty(value = "日期(字符串格式 2025年1月2日)", position = 5)
    private String expDateStr;

    /** 报销人id */
    @ApiModelProperty(value = "报销人id", position = 6)
    private String expId;

    /** 报销人 */
    @ApiModelProperty(value = "报销人", position = 7)
    private String expName;

    /** 借支单id(BIZ_FORM_CASH.ID) */
    @ApiModelProperty(value = "借支单id(BIZ_FORM_CASH.ID)", position = 8)
    private String cashId;

    /** 借支单(BIZ_FORM_CASH.SN) */
    @ApiModelProperty(value = "借支单(BIZ_FORM_CASH.SN)", position = 9)
    private String cashSn;

    /** 借支余额 */
    @ApiModelProperty(value = "借支余额", position = 10)
    private BigDecimal cashBalance;

    /** 本次冲借支 */
    @ApiModelProperty(value = "本次冲借支", position = 11)
    private BigDecimal offsetAmount;

    /** 项目合同编号 */
    @ApiModelProperty(value = "项目合同编号", position = 12)
    private String contractNo;

    /** 研发编号 */
    @ApiModelProperty(value = "研发编号", position = 13)
    private String devNo;

    /** 申请金额合计 */
    @ApiModelProperty(value = "申请金额合计", position = 14)
    private BigDecimal applyAmountTotal;

    /** 财务核定金额合计 */
    @ApiModelProperty(value = "财务核定金额合计", position = 15)
    private BigDecimal verifyAmountTotal;

    /** 核定冲借支金额 */
    @ApiModelProperty(value = "核定冲借支金额", position = 16)
    private BigDecimal verifyOffsetAmount;

    /** 核定借支单编码 */
    @ApiModelProperty(value = "核定借支单编码", position = 17)
    private String verifyCashSn;

    /** 核定付款金额 */
    @ApiModelProperty(value = "核定付款金额", position = 18)
    private BigDecimal verifyPayAmount;

    /** 出纳支付金额 */
    @ApiModelProperty(value = "出纳支付金额", position = 19)
    private BigDecimal cnPayAmount;

    /** 成本一览表-申请金额合计 */
    @ApiModelProperty(value = "成本一览表-申请金额合计", position = 20)
    private BigDecimal costApplyAmountTotal;

    /** 成本一览表-进项税合计 */
    @ApiModelProperty(value = "成本一览表-进项税合计", position = 21)
    private BigDecimal costTaxAmountTotal;

    /** 成本一览表-财务核定金额合计 */
    @ApiModelProperty(value = "成本一览表-财务核定金额合计", position = 22)
    private BigDecimal costVerifyAmountTotal;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 23)
    private String attach;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 24)
    private String memo;

    /** SN */
    @ApiModelProperty(value = "SN", position = 32)
    private String sn;

    /** 模型Id */
    @ApiModelProperty(value = "模型Id", position = 33)
    private String modelId;

    /** 流程实例Id */
    @ApiModelProperty(value = "流程实例Id", position = 34)
    private String processInstanceId;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态", position = 35)
    private String status;

    /** 冲账类型 */
    @ApiModelProperty(value = "冲账类型", position = 45)
    private String offsetType;

    /** 进项税合计 */
    @ApiModelProperty(value = "进项税合计", position = 46)
    private BigDecimal taxAmountTotal;

    /** 报销明细 - 集合 */
    @ApiModelProperty(value = "报销明细 - 集合", position = 66)
    private List<BizFormFeeItemEditParam> itemList;

    /** 成本一览表 - 集合 */
    @ApiModelProperty(value = "成本一览表 - 集合", position = 67)
    private List<BizFormFeeCostEditParam> costList;


    /** 审批意见 - 集合 */
    @ApiModelProperty(value = "审批意见 - 集合", position = 100)
    private List<FlwComment> commentList;


}
