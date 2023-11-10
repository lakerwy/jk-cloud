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
package vip.xiaonuo.sys.modular.hr.conference.rez.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 会议预约-预约会议表数据实体
 *
 * @author ljy
 * @date  2023/11/05 23:13
 **/
@Getter
@Setter
@TableName("CONFERENCE_REZ")
public class ConferenceRez {

    /** 主键 */
    @TableId
    @ApiModelProperty(value = "主键", position = 1)
    private String id;

    /** 会议室ID */
    @ApiModelProperty(value = "会议室ID", position = 2)
    private String conferenceConfigId;

    /** 预约主题 */
    @ApiModelProperty(value = "预约主题", position = 3)
    private String theme;

    /** 开始时间 */
    @ApiModelProperty(value = "开始时间", position = 4)
    private Date beginTime;

    /** 结束时间 */
    @ApiModelProperty(value = "结束时间", position = 5)
    private Date endTime;

    /** 参与者 */
    @ApiModelProperty(value = "参与者", position = 6)
    private String joinUser;

    /** 参与人数 */
    @ApiModelProperty(value = "参与人数", position = 7)
    private Integer joinSize;

    /** 预约人 */
    @ApiModelProperty(value = "预约人", position = 8)
    private String rezUser;

    /** 会议室位置 */
    @ApiModelProperty(value = "会议室位置", position = 9)
    private String location;

    /** 外部人员 */
    @ApiModelProperty(value = "外部人员", position = 10)
    private String outsideUser;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 11)
    private String remark;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 12)
    private String fileId;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 13)
    private String extJson;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 14)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 15)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 16)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 17)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 18)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /**展示时间*/
    @ApiModelProperty(value = "展示时间", position = 19)
    @TableField(exist = false)
    private String showTime;

    /** 审核状态：1 审核通过，2 审核未通过，3 会议取消 */
    @ApiModelProperty(value = "审核状态：1 审核通过，2 审核未通过，3 会议取消", position = 20)
    private Integer verifyFlag;

    /**展示时间*/
    @ApiModelProperty(value = "会议类型：普通会议，周期会议", position = 21)
    @TableField(exist = false)
    private String ConferenceType;

}
