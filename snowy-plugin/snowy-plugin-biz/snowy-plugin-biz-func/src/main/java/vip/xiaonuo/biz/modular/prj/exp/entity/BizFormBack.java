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
 * 费用报销（二期）- 归还登记实体
 *
 * @author yong.ye
 * @date  2025/02/25 09:15
 **/
@Getter
@Setter
@TableName("BIZ_FORM_BACK")
public class BizFormBack {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 借支人id */
    @ApiModelProperty(value = "借支人id", position = 2)
    private String expId;

    /** 借支人 */
    @ApiModelProperty(value = "借支人", position = 3)
    private String expName;

    /** 借支单id(BIZ_FORM_CASH.ID) */
    @ApiModelProperty(value = "借支单id(BIZ_FORM_CASH.ID)", position = 4)
    private String cashId;

    /** 借支单(BIZ_FORM_CASH.SN) */
    @ApiModelProperty(value = "借支单(BIZ_FORM_CASH.SN)", position = 5)
    private String cashSn;

    /** 借支金额 */
    @ApiModelProperty(value = "借支金额", position = 6)
    private BigDecimal cashAdvance;

    /** 借支余额 */
    @ApiModelProperty(value = "借支余额", position = 7)
    private BigDecimal cashBalance;

    /** 归还金额合计 */
    @ApiModelProperty(value = "归还金额合计", position = 8)
    private BigDecimal backAmountTotal;

    /** 未还金额 */
    @ApiModelProperty(value = "未还金额", position = 9)
    private BigDecimal whAmount;

    /** 本次归还金额 */
    @ApiModelProperty(value = "本次归还金额", position = 10)
    private BigDecimal backAmount;

    /** 归还方式 */
    @ApiModelProperty(value = "归还方式", position = 11)
    private String backType;

    /** 归还日期 */
    @ApiModelProperty(value = "归还日期", position = 12)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date backDate;

    /** 确认人Id */
    @ApiModelProperty(value = "确认人Id", position = 13)
    private String sureId;

    /** 确认人姓名 */
    @ApiModelProperty(value = "确认人姓名", position = 14)
    private String sureName;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 15)
    private String attach;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 16)
    private String memo;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 17)
    private String tenantId;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 18)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 19)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 20)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 21)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 22)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 23)
    @TableField(fill = FieldFill.INSERT)
    private String dataScopeId;

    /** SN */
    @ApiModelProperty(value = "SN", position = 24)
    private String sn;

    /** 模型Id */
    @ApiModelProperty(value = "模型Id", position = 25)
    private String modelId;

    /** 流程实例Id */
    @ApiModelProperty(value = "流程实例Id", position = 26)
    private String processInstanceId;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态", position = 27)
    private String status;
}
