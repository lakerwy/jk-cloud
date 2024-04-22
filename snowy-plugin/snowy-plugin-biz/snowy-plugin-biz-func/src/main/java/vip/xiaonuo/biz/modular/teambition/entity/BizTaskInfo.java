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
package vip.xiaonuo.biz.modular.teambition.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import vip.xiaonuo.biz.modular.teambition.taskInfoComment.entity.BizTaskComment;
import vip.xiaonuo.biz.modular.teambition.vo.ChildTaskInfoVo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 任务主表实体
 *
 * @author ljy
 * @date  2023/12/08 09:03
 **/
@Getter
@Setter
@TableName("BIZ_TASK_INFO")
public class BizTaskInfo {

    /** 主键 */
    @TableId
    @ApiModelProperty(value = "主键", position = 1)
    private String id;

    /** 租户ID */
    @ApiModelProperty(value = "租户ID", position = 2)
    private String tenantId;

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

    /** 任务实际开始时间 */
    @ApiModelProperty(value = "任务实际开始时间", position = 7)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date taskStartTime;

    /** 任务实际结束时间 */
    @ApiModelProperty(value = "任务实际结束时间", position = 8)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date taskEndTime;

    /** 任务执行者 */
    @ApiModelProperty(value = "任务执行者-主办人员", position = 9)
    private String belonger;

    /** 任务状态 */
    @ApiModelProperty(value = "任务状态，0 未确认，1 已确认，2 已取消，3 进行中，4 已完成", position = 10)
    private String taskStatus;

    /** 任务优先级：0 最低，1较低，2普通，3较高，4 最高*/
    @ApiModelProperty(value = "任务优先级(默认为最低 0)：P0 最低，P1 较低，P2 普通，P3 较高，P4 最高", position = 11)
    private String taskPriority;

    /** 参与人集合，参与人ID，逗号隔开 --  协办人员 */
    @ApiModelProperty(value = "参与人集合，参与人ID，逗号隔开 --  协办人员", position = 12)
    private String taskParticipants;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 14)
    private String dataScopeId;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 15)
    private String extJson;

    /** 删除标识 */
    @ApiModelProperty(value = "删除标识", position = 16)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 17)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty(value = "创建人", position = 18)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间", position = 19)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty(value = "更新人", position = 20)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 负责人ID */
    @ApiModelProperty(value = "负责人ID", position = 21)
    private String leadId;

    /** 负责人部门 */
    @ApiModelProperty(value = "负责人部门", position = 22)
    private String leadOrg;

    /** 任务来源详情表 */
    @ApiModelProperty(value = "任务来源详情表", position = 23)
    @TableField(exist = false)
    private BizTaskProject bizTaskProject;

    /** 任务执行者部门 */
    @ApiModelProperty(value = "任务执行者部门", position = 24)
    private String belongerOrg;

    /** 任务评论 */
    @ApiModelProperty(value = "任务评论", position = 25)
    @TableField(exist = false)
    private List<BizTaskComment> bizTaskCommentList;

    /** 任务附件 */
    /*@ApiModelProperty(value = "任务附件", position = 25)
    @TableField(exist = false)
    private List<BizTaskFile> bizTaskFileList;*/

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

    /** 钉钉日程ID */
    @ApiModelProperty(value = "钉钉日程ID", position = 31)
    private String scheduleId;

    /** 文件Id */
    @ApiModelProperty(value = "文件Id", position = 32)
    private String fileId;

    /** 子任务 */
    @ApiModelProperty(value = "子任务", position = 33)
    @TableField(exist = false)
    private List<ChildTaskInfoVo> children;

    /** 任务预期开始时间 */
    @ApiModelProperty(value = "任务预期开始时间", position = 34)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date expectStartTime;

    /** 任务预期结束时间 */
    @ApiModelProperty(value = "任务预期结束时间", position = 35)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date expectEndTime;
}
