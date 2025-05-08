package vip.xiaonuo.biz.modular.crm.subcontract.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 分包合同结算明细实体
 *
 * @author pzh
 * @date  2025/04/30 11:20
 **/
@Getter
@Setter
@TableName("BIZ_CRM_SUBCONTRACT_SETTLEMENT_ITEM")
public class SubcontractSettlementItem {

    /** 主键 */
    @TableId
    @ApiModelProperty(value = "主键", position = 1)
    private String id;

    /** 关联-分包合同结算审批ID */
    @ApiModelProperty(value = "关联-分包合同结算审批ID", position = 2)
    private String subcontractSettlementId;

    /** 分包合同主键ID */
    @ApiModelProperty(value = "分包合同主键ID", position = 3)
    private String subcontractId;

    /** 分包合同编号 */
    @ApiModelProperty(value = "分包合同编号", position = 4)
    private String subcontractNo;

    /** 分包合同名称 */
    @ApiModelProperty(value = "分包合同名称", position = 5)
    private String subcontractName;

    /** 分包合同乙方 */
    @ApiModelProperty(value = "分包合同乙方", position = 6)
    private String counterparty;

    /** 分包合同金额 */
    @ApiModelProperty(value = "分包合同金额", position = 7)
    private BigDecimal subcontractAmount;

    /** 结算金额（累计已拨付） */
    @ApiModelProperty(value = "结算金额（累计已拨付）", position = 8)
    private BigDecimal settlementAmountTotal;

    /** 结算时间 */
    @ApiModelProperty(value = "结算时间", position = 9)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date settlementTime;

    /** 结算说明 */
    @ApiModelProperty(value = "结算说明", position = 10)
    private String settlementDesc;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 11)
    private String attachments;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 12)
    private String memo;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 13)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 14)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 15)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 16)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 17)
    private String tenantId;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 18)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 19)
    private String dataScopeId;
}
