/*
 * Copyright [2022] [https://www.xiaonuo.vip]
 *
 * Snowy采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：
 *
 * 1.请不要删除和修改根目录下的LICENSE文件。
 * 2.请不要删除和修改Snowy源码头部的版权声明。
 * 3.本项目代码可免费商业使用，商业使用请保留源码和相关描述文件的项目出处，作者声明等。
 * 4.分发源码时候，请注明软件出处 https://www.xiaonuo.vip
 * 5.不可二次分发开源参与同类竞品，如有想法可联系团队xiaonuobase@qq.com商议合作。
 * 6.若您的项目无法满足以上几点，需要更多功能代码，获取Snowy商业授权许可，请在官网购买授权，地址为 https://www.xiaonuo.vip
 */
package vip.xiaonuo.biz.modular.invoice.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 财务系统-项目回款录入申请实体
 *
 * @author yong.ye
 * @date  2024/03/19 10:24
 **/
@Getter
@Setter
@TableName("BIZ_INVOICE_PRJ_REFUND")
public class InvoicePrjRefund {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 申请人单位：总部|分公司|零星 */
    @ApiModelProperty(value = "申请人单位：总部|分公司|零星", position = 2)
    private String applyUnit;

    /** 申请人Id */
    @ApiModelProperty(value = "申请人Id", position = 3)
    private String applyId;

    /** 申请人姓名 */
    @ApiModelProperty(value = "申请人姓名", position = 4)
    private String applyName;

    /** 收款日期 */
    @ApiModelProperty(value = "收款日期", position = 5)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @TableField(insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private Date skDate;

    /** 收款编号 */
    @ApiModelProperty(value = "收款编号", position = 6)
    private String recordSn;

    /** 收款单位 */
    @ApiModelProperty(value = "收款单位", position = 8)
    private String unitName;

    /** 收款金额 */
    @ApiModelProperty(value = "收款金额", position = 9)
    @TableField(insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private BigDecimal amount;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 10)
    private String memo;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 11)
    private String tenantId;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 12)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

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

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 17)
    @TableField(fill = FieldFill.INSERT)
    private String dataScopeId;

    /** SN流水号 */
    @ApiModelProperty(value = "SN流水号", position = 18)
    private String sn;

    /** 模型Id */
    @ApiModelProperty(value = "模型Id", position = 19)
    private String modelId;

    /** 流程实例Id */
    @ApiModelProperty(value = "流程实例Id", position = 20)
    private String processInstanceId;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态", position = 21)
    private String status;

    /** 入参信息 */
    @ApiModelProperty(value = "入参信息", position = 22)
    private String extJson;
}
