package vip.xiaonuo.biz.modular.prjFee.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 工程款拨付 - 款项拨付实体
 *
 * @author pzh
 * @date  2025/01/15 09:33
 **/
@Getter
@Setter
@TableName("BIZ_PRJ_FEE_ALLOTMENT_ITEM")
public class PrjFeeAllotmentItem {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 关联工程款拨付ID（BIZ_PRJ_FEE_ALLOTMENT.ID） */
    @ApiModelProperty(value = "关联工程款拨付ID（BIZ_PRJ_FEE_ALLOTMENT.ID）", position = 2)
    private String prjFeeAllotmentId;

    /** 分包合同编号 */
    @ApiModelProperty(value = "分包合同编号", position = 3)
    private String contractNo;

    /** 乙方（分包单位名称、收款单位） */
    @ApiModelProperty(value = "乙方（分包单位名称、收款单位）", position = 4)
    private String counterparty;

    /** 业务内容 */
    @ApiModelProperty(value = "业务内容", position = 5)
    private String businessType;

    /** 合同金额 */
    @ApiModelProperty(value = "合同金额", position = 6)
    private BigDecimal contractAmount;

    /** 发票金额（累计） */
    @ApiModelProperty(value = "发票金额（累计）", position = 7)
    private BigDecimal invoiceAmountAcc;

    /** 已付金额 */
    @ApiModelProperty(value = "已付金额", position = 8)
    private BigDecimal payAmount;

    /** 已付金额中垫付款金额 */
    @ApiModelProperty(value = "已付金额中垫付款金额", position = 9)
    private BigDecimal advancedAmount;

    /** 已开票未付金额 */
    @ApiModelProperty(value = "已开票未付金额", position = 10)
    private BigDecimal invoicedNotPayAmount;

    /** 本次计划拨付金额 */
    @ApiModelProperty(value = "本次计划拨付金额", position = 11)
    private BigDecimal planDisburseAmount;

    /** 出纳支付金额 */
    @ApiModelProperty(value = "出纳支付金额", position = 12)
    private BigDecimal cashierPaymentAmount;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 13)
    private String attachments;

    /** 文件名 */
    @ApiModelProperty(value = "文件名", position = 13)
    private String fileName;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 14)
    private String tenantId;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 15)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 16)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 17)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间", position = 18)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 更新用户 */
    @ApiModelProperty(value = "更新用户", position = 19)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 20)
    @TableField(fill = FieldFill.INSERT)
    private String dataScopeId;
}
