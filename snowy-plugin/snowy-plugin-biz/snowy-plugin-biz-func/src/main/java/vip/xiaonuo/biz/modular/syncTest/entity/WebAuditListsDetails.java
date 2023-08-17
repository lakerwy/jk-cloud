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
package vip.xiaonuo.biz.modular.syncTest.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 业务表-设计校审记录表实体
 *
 * @author mayike
 * @date  2023/08/16 09:24
 **/
@Getter
@Setter
@TableName("WEB_AUDIT_LISTS_DETAILS")
public class WebAuditListsDetails {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 流程单号 */
    @ApiModelProperty(value = "流程单号", position = 2)
    private Integer taskId;

    /** ORDER_INDEX */
    @ApiModelProperty(value = "ORDER_INDEX", position = 3)
    private Integer orderIndex;

    /** TIMES */
    @ApiModelProperty(value = "TIMES", position = 4)
    private Integer times;

    /** 校审意见 */
    @ApiModelProperty(value = "校审意见", position = 5)
    private String opinion;

    /** 处理情况 */
    @ApiModelProperty(value = "处理情况", position = 6)
    @TableField(value = "`condition`")
    private String condition;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 7)
    private String auditAttachments;

    /** 工程设计校审主表ID */
    @ApiModelProperty(value = "工程设计校审主表ID", position = 8)
    private String auditId;

    /** 校审时间 */
    @ApiModelProperty(value = "校审时间", position = 9)
    private Date opinionTime;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 10)
    private Date reviseDate;

    /** 修订说明 */
    @ApiModelProperty(value = "修订说明", position = 11)
    private String reviseExplain;

    /** 修订附件地址 */
    @ApiModelProperty(value = "修订附件地址", position = 12)
    private String reviseAttachments;

    /** 删除状态 */
    @ApiModelProperty(value = "删除状态", position = 13)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 14)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty(value = "创建人", position = 15)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间", position = 16)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty(value = "更新人", position = 17)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;
}
