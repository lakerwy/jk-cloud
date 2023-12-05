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
package vip.xiaonuo.sys.modular.hr.conference.userSign.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 会议预约-签到表实体
 *
 * @author ljy
 * @date  2023/11/24 09:27
 **/
@Getter
@Setter
@TableName("CONFERENCE_USER_SIGN")
public class ConferenceUserSign {

    /** ID */
    @TableId
    @ApiModelProperty(value = "ID", position = 1)
    private String id;

    /** 会议室ID */
    @ApiModelProperty(value = "会议室ID", position = 2)
    private String conferenceConfigId;

    /** 会议室名称 */
    @ApiModelProperty(value = "会议室名称", position = 3)
    private String conferenceConfigName;

    /** 预约会议ID */
    @ApiModelProperty(value = "预约会议ID", position = 4)
    private String rezId;

    /** 签到人名称 */
    @ApiModelProperty(value = "签到人名称", position = 5)
    private String userSignName;

    /** 手机号 */
    @ApiModelProperty(value = "手机号", position = 6)
    private String phone;

    /** 签到部门名称 */
    @ApiModelProperty(value = "签到部门名称", position = 7)
    private String orgSignName;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 8)
    private String extJson;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 9)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 10)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 11)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 12)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 13)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 预约主题 */
    @ApiModelProperty(value = "预约主题", position = 14)
    private String theme;

    /** 用户ID */
    @ApiModelProperty(value = "用户ID", position = 15)
    private String userId;

    /** 是否外部人员 ,1 为外部人员签到 */
    @ApiModelProperty(value = "是否外部人员，1 为外部人员签到", position = 16)
    private Integer isOutside;

    /** 是否同意参加会议，1 参加，2 不同意 */
    @ApiModelProperty(value = "是否同意参加会议，1 参加，2 不同意", position = 17)
    private Integer isAgree;
}
