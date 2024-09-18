package vip.xiaonuo.biz.modular.prj.expense.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 财务管理 - 借支单 - 申请流程实体
 *
 * @author yong.ye
 * @date  2024/07/30 15:33
 **/
@Getter
@Setter
@TableName("BIZ_DEBIT_APPLY")
public class BizDebitApply {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 发起人 */
    @ApiModelProperty(value = "发起人", position = 2)
    private String initor;

    /** 发起人 id */
    @ApiModelProperty(value = "发起人 id", position = 2)
    private String initorId;

    /** 财务编码 */
    @ApiModelProperty(value = "财务编码", position = 3)
    private String finCode;

    /** 单位 */
    @ApiModelProperty(value = "单位", position = 4)
    private String unit;

    /** 部门 */
    @ApiModelProperty(value = "部门", position = 5)
    private String dept;

    /** 日期 */
    @ApiModelProperty(value = "日期", position = 6)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date expDate;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 7)
    private String memo;

    /** 借支人 */
    @ApiModelProperty(value = "借支人", position = 8)
    private String borrower;

    /** 职务 */
    @ApiModelProperty(value = "职务", position = 9)
    private String duty;

    /** 借支事由 */
    @ApiModelProperty(value = "借支事由", position = 10)
    private String reason;

    /** 人民币（大写） */
    @ApiModelProperty(value = "人民币（大写）", position = 11)
    private String upAmount;

    /** 人民币（小写） */
    @ApiModelProperty(value = "人民币（小写）", position = 12)
    private String lowAmount;

    /** 预计归还时间 */
    @ApiModelProperty(value = "预计归还时间", position = 13)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date returnDate;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 14)
    private String attach;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 15)
    private String tenantId;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 16)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 17)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 18)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 19)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 20)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 21)
    @TableField(fill = FieldFill.INSERT)
    private String dataScopeId;

    /** SN流水号 */
    @ApiModelProperty(value = "SN流水号", position = 22)
    private String sn;

    /** 模型Id */
    @ApiModelProperty(value = "模型Id", position = 23)
    private String modelId;

    /** 流程实例Id */
    @ApiModelProperty(value = "流程实例Id", position = 24)
    private String processInstanceId;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态", position = 25)
    private String status;

    /** 入参信息 */
    @ApiModelProperty(value = "入参信息", position = 26)
    private String extJson;

    /** 借支 - 还款状态 0：待归还  1：已归还 */
    @ApiModelProperty(value = "借支 - 还款状态 0：待归还  1：已归还", position = 27)
    private String debitStatus;

    /** 收款账户信息-账户名 */
    @ApiModelProperty(value = "收款账户信息-账户名", position = 31)
    private String bankName;

    /** 收款账户信息-账户号 */
    @ApiModelProperty(value = "收款账户信息-账户号", position = 32)
    private String bankNo;

    /** 收款账户信息-开户行 */
    @ApiModelProperty(value = "收款账户信息-开户行", position = 33)
    private String bankAccount;

    /** 收款账户信息-备注 */
    @ApiModelProperty(value = "收款账户信息-备注", position = 34)
    private String bankMemo;
}
