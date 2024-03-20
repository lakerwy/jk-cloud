package vip.xiaonuo.biz.modular.teambition.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BizWorkTaskInfoVo {

    /** 主键 */
    @ApiModelProperty(value = "主键", position = 1)
    private String id;

    /** 父任务ID */
    @ApiModelProperty(value = "父任务ID", position = 3)
    private String parentId;

    /** 任务名称 */
    @ApiModelProperty(value = "任务名称", position = 4)
    private String taskName;

    /** 项目备注 */
    @ApiModelProperty(value = "任务备注", position = 5)
    private String taskRemark;

    /** 任务来源详情ID */
    @ApiModelProperty(value = "任务来源详情ID", position = 6)
    private String proId;

    /** 任务开始时间 */
    @ApiModelProperty(value = "任务开始时间", position = 7)
    private Date taskStartTime;

    /** 任务结束时间 */
    @ApiModelProperty(value = "任务结束时间", position = 8)
    private Date taskEndTime;

    /** 任务执行者 */
    @ApiModelProperty(value = "任务执行者-主办人员", position = 9)
    private String belonger;

    /** 任务状态 */
    @ApiModelProperty(value = "任务状态，0 已取消，1 进行中，2 已完成", position = 10)
    private String taskStatus;

    /** 任务优先级：0 最低，1较低，2普通，3较高，4 最高*/
    @ApiModelProperty(value = "任务优先级(默认为最低 0)：0 最低，1 较低，2 普通，3 较高，4 最高", position = 11)
    private String taskPriority;

    /** 参与人集合，参与人ID，逗号隔开 --  协办人员 */
    @ApiModelProperty(value = "参与人集合，参与人ID，逗号隔开 --  协办人员", position = 12)
    private String taskParticipants;

    /** 负责人ID */
    @ApiModelProperty(value = "负责人ID", position = 21)
    private String leadId;

    /** 负责人部门 */
    @ApiModelProperty(value = "负责人部门", position = 22)
    private String leadOrg;

    /** 任务执行者部门 */
    @ApiModelProperty(value = "任务执行者部门", position = 24)
    private String belongerOrg;


    /** 汇报对象 -- 多人，逗号隔开 */
    @ApiModelProperty(value = "汇报对象 -- 多人，逗号隔开", position = 26)
    private String reportUser;

    /** 抄送人 - 多人逗号隔开 */
    @ApiModelProperty(value = "抄送人 - 多人逗号隔开", position = 27)
    private String copyNoticeUser;

    /** 定时提醒：提前1天，提前1小时 */
    @ApiModelProperty(value = "定时提醒：提前1天，提前1小时", position = 28)
    private String remindPeriod;

    /** 任务编码 */
    @ApiModelProperty(value = "任务编码", position = 29)
    private String sn;

    /** 任务来源类别 */
    @ApiModelProperty(value = "任务来源类别", position = 30)
    private String proType;
}
