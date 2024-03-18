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
 * 会议模块-工作部署实体
 *
 * @author ljy
 * @date  2023/12/07 17:43
 **/
@Getter
@Setter
@TableName("CONFERENCE_WORK_ARRANGE")
public class ConferenceWorkArrange {

    /** ID */
    @TableId
    @ApiModelProperty(value = "ID", position = 1)
    private String id;

    /** 会议ID */
    @ApiModelProperty(value = "会议ID", position = 2)
    private String rezId;

    /** 部署事项 */
    @ApiModelProperty(value = "部署事项", position = 3)
    private String workArrange;

    /** 任务时间 */
    @ApiModelProperty(value = "任务开始时间", position = 4)
    private Date startTime;

    /** 完成时间 */
    @ApiModelProperty(value = "完成时间", position = 5)
    private Date finishTime;

    /** 责任人ID */
    @ApiModelProperty(value = "责任人ID", position = 6)
    private String dutyUserId;

    /** 工作进展：任务状态，0 已取消，1 进行中，2 已完成*/
    @ApiModelProperty(value = "工作进展：任务状态，0 已取消，1 进行中，2 已完成", position = 7)
    private Integer workEvolve;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 9)
    private String extJson;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 10)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 11)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 12)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 13)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 14)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 是否创建日程：0 未创建，1 为已创建 */
    @ApiModelProperty(value = "是否创建日程：0 未创建，1 为已创建", position = 15)
    private Integer isSchedule;

    /** 任务参与人，存在多个，英文逗号隔开 *//*
    @ApiModelProperty(value = "任务参与人，存在多个，英文逗号隔开", position = 16)
    private String joinUsers;*/

    /** 会议主题 */
    @ApiModelProperty(value = "会议主题", position = 16)
    @TableField(exist = false)
    private String rezTheme;

    /** 钉钉日程ID */
    @ApiModelProperty(value = "钉钉日程ID", position = 17)
    private String scheduleId;

    /** 责任部门ID */
    @ApiModelProperty(value = "责任部门ID", position = 18)
    private String dutyOrgId;
}
