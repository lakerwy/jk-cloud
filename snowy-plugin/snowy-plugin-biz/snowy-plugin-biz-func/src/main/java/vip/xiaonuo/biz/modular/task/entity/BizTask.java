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
package vip.xiaonuo.biz.modular.task.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 业务表-绩效考核-任务表实体
 *
 * @author yong.ye
 * @date  2023/08/04 14:20
 **/
@Getter
@Setter
@TableName("BIZ_TASK")
public class BizTask {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 组织Id */
    @ApiModelProperty(value = "组织Id", position = 2)
    private String orgId;

    /** 责任人名称 */
    @ApiModelProperty(value = "责任人名称", position = 3)
    private String ower;

    /** 责任人Id */
    @ApiModelProperty(value = "责任人Id", position = 3)
    private String owerId;

    /** 年度 */
    @ApiModelProperty(value = "年度", position = 3)
    private String year;

    /** 年度重点/专项工作任务 */
    @ApiModelProperty(value = "年度重点/专项工作任务/计划名称", position = 4)
    private String taskName;

    /** 开始时间 */
    @ApiModelProperty(value = "开始时间", position = 5)
    @TableField(insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private Date startDate;

    /** 完成时间 */
    @ApiModelProperty(value = "完成时间", position = 6)
    @TableField(insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private Date endDate;

    /** 过程节点 */
    @ApiModelProperty(value = "过程节点", position = 7)
    private String node;

    /** 标志成果 */
    @ApiModelProperty(value = "标志成果", position = 8)
    private String markResult;

    /** 状态 */
    @ApiModelProperty(value = "状态", position = 9)
    private String status;

    /** 证明附件 */
    @ApiModelProperty(value = "证明附件", position = 10)
    private String attach;

    /** 董事办评分 */
    @ApiModelProperty(value = "董事办评分", position = 13)
    private Double boosScore;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 14)
    private String memo;

    /** 删除状态标记 */
    @ApiModelProperty(value = "删除状态标记", position = 15)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 16)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty(value = "创建人", position = 17)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间", position = 18)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty(value = "更新人", position = 19)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 流程审批单 - 外键Id */
    @ApiModelProperty(value = "流程审批单 - 外键Id", position = 20)
    private String flwId;

    /** 组织名称 */
    @ApiModelProperty(value = "组织名称", position = 21)
    @TableField(exist = false)
    private String orgName;

    /** 完成进度 百分比 100% 存 100 */
    @ApiModelProperty(value = "完成进度 百分比 100% 存 100", position = 22)
    private Double finishStatus;

    /** 实际完成时间 */
    @ApiModelProperty(value = "实际完成时间", position = 23)
    private Date finishDate;

    /** 董事长意见 */
    @ApiModelProperty(value = "董事办意见", position = 24)
    private String boosMemo;

    /** 权重（存小数 0.7） */
    @ApiModelProperty(value = "权重（存小数 0.7）", position = 25)
    private Double rate;

    /** 进度分系数（系统自动计算） */
    @ApiModelProperty(value = "进度分系数（系统自动计算）", position = 26)
    private Double progressRate;

    /** 人工调整分 */
    @ApiModelProperty(value = "人工调整分", position = 27)
    private Double adjScore;

    /** 进度分 */
    @ApiModelProperty(value = "进度分", position = 28)
    @TableField(exist = false)
    private Double progressScore;

    /** 成效分 */
    @ApiModelProperty(value = "成效分", position = 29)
    @TableField(exist = false)
    private Double effectScore;

    /** 任务进度分系数自动计算留痕备注 */
    @ApiModelProperty(value = "任务进度分系数自动计算留痕备注", position = 30)
    private String autoMemo;

    /** 董事长评分流程实例Id */
    @ApiModelProperty(value = "董事长评分流程实例Id", position = 31)
    private String bossProcessInstanceId;

    /** 绩效评估流程实例Id - 绩效评估 BIZ_TASK_RATING.processInstanceId */
    @ApiModelProperty(value = "绩效评估流程实例Id - 绩效评估 BIZ_TASK_RATING.processInstanceId", position = 100)
    @TableField(exist = false)
    private String taskRatingProcessInstanceId;

    /** =================组织绩效需求-新增的字段================= */

    /** 序号 */
    @ApiModelProperty(value = "序号", position = 200)
    private BigDecimal indexNum;

    /** 职能模块 */
    @ApiModelProperty(value = "职能模块", position = 201)
    private String funModule;

    /** 级别 */
    @ApiModelProperty(value = "级别", position = 201)
    private String level;

    /** 计划说明及完成标准 */
    @ApiModelProperty(value = "计划说明及完成标准", position = 202)
    private String taskDesc;

    /** 计划说明及完成标准 */
    @ApiModelProperty(value = "计划前置条件", position = 203)
    private String taskCondition;

    /** 配合部门 */
    @ApiModelProperty(value = "配合部门", position = 203)
    private String coopDept;

    /** 配合部门 */
    @ApiModelProperty(value = "配合部门ID", position = 204)
    private String coopDeptId;

    /** 实际完成日期 */
    @ApiModelProperty(value = "实际完成日期（格式：yyyy-MM-dd）", position = 205)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date actualFinishDate;

}
