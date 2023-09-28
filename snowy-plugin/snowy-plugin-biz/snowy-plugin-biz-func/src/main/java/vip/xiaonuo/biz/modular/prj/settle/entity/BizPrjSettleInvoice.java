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
package vip.xiaonuo.biz.modular.prj.settle.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 业务表-项目款结算单-本次开票实体
 *
 * @author yong.ye
 * @date  2023/09/28 11:37
 **/
@Getter
@Setter
@TableName("BIZ_PRJ_SETTLE_INVOICE")
public class BizPrjSettleInvoice {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 发票内容 */
    @ApiModelProperty(value = "发票内容", position = 2)
    private String content;

    /** 普票/专票 */
    @ApiModelProperty(value = "普票/专票", position = 3)
    private String type;

    /** 开票日期 */
    @ApiModelProperty(value = "开票日期", position = 4)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date kpDate;

    /** 份数 */
    @ApiModelProperty(value = "份数", position = 5)
    private String page;

    /** 税率 */
    @ApiModelProperty(value = "税率", position = 6)
    private String rate;

    /** 税额 */
    @ApiModelProperty(value = "税额", position = 7)
    private Double seAmount;

    /** 开票金额（税额） */
    @ApiModelProperty(value = "开票金额（税额）", position = 8)
    private Double kpAmount;

    /** 删除状态标记 */
    @ApiModelProperty(value = "删除状态标记", position = 9)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 10)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty(value = "创建人", position = 11)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间", position = 12)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty(value = "更新人", position = 13)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 14)
    private String memo;

    /** 项目款结算单主表主键BIZ_PRJ_SETTLE.Id */
    @ApiModelProperty(value = "项目款结算单主表主键BIZ_PRJ_SETTLE.Id", position = 15)
    private String settleId;
}
