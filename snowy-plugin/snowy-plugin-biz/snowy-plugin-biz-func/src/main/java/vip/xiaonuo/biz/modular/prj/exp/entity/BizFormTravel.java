package vip.xiaonuo.biz.modular.prj.exp.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 费用报销（二期）- 差旅费用报销单实体
 *
 * @author yong.ye
 * @date  2025/02/24 15:17
 **/
@Getter
@Setter
@TableName("BIZ_FORM_TRAVEL")
public class BizFormTravel {

    /** 主键ID */
    @TableId
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

    /** 交通费合计 */
    @ApiModelProperty(value = "交通费合计", position = 14)
    private BigDecimal jtAmountTotal;

    /** 住宿费合计 */
    @ApiModelProperty(value = "住宿费合计", position = 15)
    private BigDecimal zsAmountTotal;

    /** 差旅补贴合计 */
    @ApiModelProperty(value = "差旅补贴合计", position = 16)
    private BigDecimal clbtAmountTotal;

    /** 其他合计 */
    @ApiModelProperty(value = "其他合计", position = 17)
    private BigDecimal otherAmountTotal;

    /** 申请报销金额合计 */
    @ApiModelProperty(value = "申请报销金额合计", position = 18)
    private BigDecimal applyAmountTotal;

    /** 核定总额 */
    @ApiModelProperty(value = "核定总额", position = 19)
    private BigDecimal verifyAmountTotal;

    /** 核定冲借支金额 */
    @ApiModelProperty(value = "核定冲借支金额", position = 20)
    private BigDecimal verifyOffsetAmount;

    /** 核定借支单编码 */
    @ApiModelProperty(value = "核定借支单编码", position = 21)
    private String verifyCashSn;

    /** 核定借支单ID */
    @ApiModelProperty(value = "核定借支单ID", position = 21)
    private String verifyCashId;

    /** 财务核定拨付金额 */
    @ApiModelProperty(value = "财务核定拨付金额", position = 22)
    private BigDecimal verifyPayAmount;

    /** 出纳支付金额 */
    @ApiModelProperty(value = "出纳支付金额", position = 23)
    private BigDecimal cnPayAmount;

    /** 成本一览表-申请金额合计 */
    @ApiModelProperty(value = "成本一览表-申请金额合计", position = 24)
    private BigDecimal costApplyAmountTotal;

    /** 成本一览表-进项税合计 */
    @ApiModelProperty(value = "成本一览表-进项税合计", position = 25)
    private BigDecimal costTaxAmountTotal;

    /** 成本一览表-财务核定金额合计 */
    @ApiModelProperty(value = "成本一览表-财务核定金额合计", position = 26)
    private BigDecimal costVerifyAmountTotal;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 27)
    private String attach;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 28)
    private String memo;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 29)
    private String tenantId;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 30)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 31)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 32)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 33)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 34)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 35)
    @TableField(fill = FieldFill.INSERT)
    private String dataScopeId;

    /** SN */
    @ApiModelProperty(value = "SN", position = 36)
    private String sn;

    /** 模型Id */
    @ApiModelProperty(value = "模型Id", position = 37)
    private String modelId;

    /** 流程实例Id */
    @ApiModelProperty(value = "流程实例Id", position = 38)
    private String processInstanceId;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态", position = 39)
    private String status;


    /** 冲账类型 */
    @ApiModelProperty(value = "冲账类型", position = 45)
    private String offsetType;

    /** 借支金额|投标费用金额 */
    @ApiModelProperty(value = "借支金额|投标费用金额", position = 46)
    private BigDecimal initAmount;

}
