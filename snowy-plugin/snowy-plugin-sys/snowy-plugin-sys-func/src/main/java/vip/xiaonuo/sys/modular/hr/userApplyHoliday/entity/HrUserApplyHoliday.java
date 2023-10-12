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
package vip.xiaonuo.sys.modular.hr.userApplyHoliday.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 请假申请表实体
 *
 * @author ljy
 * @date  2023/09/22 17:05
 **/
@Getter
@Setter
@TableName("HR_USER_APPLY_HOLIDAY")
public class HrUserApplyHoliday {

    /** 主键 */
    @TableId
    @ApiModelProperty(value = "主键", position = 1)
    private String id;

    /** 租户ID */
    @ApiModelProperty(value = "租户ID", position = 2)
    private String tenantId;

    /** 申请人ID */
    @ApiModelProperty(value = "申请人ID", position = 3)
    private String applyUserId;

    /** 申请人名称 */
    @ApiModelProperty(value = "申请人名称", position = 4)
    private String applyUserName;

    /** 申请类型：1 事假，2 调休，3 年假 */
    @ApiModelProperty(value = "申请类型：申请类型：事假 1，病假 2，婚假 3，产假 4，丧假 5，年假 6，调休 7", position = 5)
    private Integer applyType;

    /** 申请开始时间 */
    @ApiModelProperty(value = "申请开始时间", position = 6)
    private Date applyBeginTime;

    /** 申请结束时间 */
    @ApiModelProperty(value = "申请结束时间", position = 7)
    private Date applyEndTime;

    /** 申请理由 */
    @ApiModelProperty(value = "申请理由", position = 8)
    private String applyReason;

    /** 模型ID */
    @ApiModelProperty(value = "模型ID", position = 9)
    private String modelId;

    /** 流程实例id */
    @ApiModelProperty(value = "流程实例id", position = 10)
    private String processInstanceId;

    /** SN单号 */
    @ApiModelProperty(value = "SN单号", position = 11)
    private String sn;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态", position = 12)
    private String status;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 13)
    private String extJson;

    /** 删除标识 */
    @ApiModelProperty(value = "删除标识", position = 14)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 15)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty(value = "创建人", position = 16)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间", position = 17)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty(value = "更新人", position = 18)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 请假天数 */
    @ApiModelProperty(value = "请假天数", position = 19)
    private Double leaveDays;

}
