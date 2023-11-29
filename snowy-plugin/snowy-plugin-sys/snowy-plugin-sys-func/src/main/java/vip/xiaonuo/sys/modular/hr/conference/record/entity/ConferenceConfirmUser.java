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
package vip.xiaonuo.sys.modular.hr.conference.record.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

/**
 * 会议记录确认表实体
 *
 * @author ljy
 * @date  2023/11/28 22:28
 **/
@Getter
@Setter
@TableName("CONFERENCE_CONFIRM_USER")
public class ConferenceConfirmUser {

    /** ID */
    @TableId
    @ApiModelProperty(value = "ID", position = 1)
    private String id;

    /** 会议记录ID */
    @ApiModelProperty(value = "会议记录ID", position = 2)
    private String recordId;

    /** 会议纪要确认人ID */
    @ApiModelProperty(value = "会议纪要确认人ID", position = 3)
    private String confirmUserId;

    /** 会议纪要确认人名称 */
    @ApiModelProperty(value = "会议纪要确认人名称", position = 4)
    private String confirmUserName;

    /** 是否对会议进行确认，1 已确认 */
    @ApiModelProperty(value = "是否对会议进行确认，1 已确认", position = 5)
    private Integer status;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 6)
    private String extJson;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 7)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 8)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 9)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 10)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 11)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 会议预约ID */
    @ApiModelProperty(value = "会议预约ID", position = 12)
    private String rezId;

    /** 会议主题 */
    @ApiModelProperty(value = "会议主题", position = 13)
    private String theme;
}
