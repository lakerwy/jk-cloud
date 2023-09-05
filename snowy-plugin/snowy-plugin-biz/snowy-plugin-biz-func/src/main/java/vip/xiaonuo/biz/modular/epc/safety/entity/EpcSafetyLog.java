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
package vip.xiaonuo.biz.modular.epc.safety.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

/**
 * EPC-安全日志实体
 *
 * @author yong.ye
 * @date  2023/09/05 14:58
 **/
@Getter
@Setter
@TableName("EPC_SAFETY_LOG")
public class EpcSafetyLog {

    /** 主键Id */
    @TableId
    @ApiModelProperty(value = "主键Id", position = 1)
    private String id;

    /** 租户Id */
    @ApiModelProperty(value = "租户Id", position = 2)
    private String tenantId;

    /** 项目ID(外键) */
    @ApiModelProperty(value = "项目ID(外键)", position = 3)
    private String projectId;

    /** 时间 */
    @ApiModelProperty(value = "时间", position = 4)
    private Date logTime;

    /** 星期几 */
    @ApiModelProperty(value = "星期几", position = 5)
    private String weekDay;

    /** 天气情况 */
    @ApiModelProperty(value = "天气情况", position = 6)
    private String weather;

    /** 巡查情况 */
    @ApiModelProperty(value = "巡查情况", position = 7)
    private String patrolCondition;

    /** 工人安全教育情况 */
    @ApiModelProperty(value = "工人安全教育情况", position = 8)
    private String workerCondition;

    /** 安全设施进场情况 */
    @ApiModelProperty(value = "安全设施进场情况", position = 9)
    private String facitilyCondition;

    /** 其他安全情况 */
    @ApiModelProperty(value = "其他安全情况", position = 10)
    private String otherCondition;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 11)
    private String attach;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 12)
    private String memo;

    /** 删除状态标记 */
    @ApiModelProperty(value = "删除状态标记", position = 13)
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
