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
package vip.xiaonuo.sys.modular.hr.tryTurn.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * HR - 试用期转正考核实体
 *
 * @author yong.ye
 * @date  2024/06/14 08:39
 **/
@Getter
@Setter
@TableName("HR_TRY_TURN")
public class TryTurn {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** HR - 试用期考核责任 - 主键Id */
    @ApiModelProperty(value = "HR - 试用期考核责任 - 主键Id", position = 2)
    private String hrTryDutyId;

    /** 考核类型 */
    @ApiModelProperty(value = "考核类型", position = 3)
    private String type;

    /** 毕业时间 */
    @ApiModelProperty(value = "毕业时间", position = 4)
    private String graduateDate;

    /** 最高职称 */
    @ApiModelProperty(value = "最高职称", position = 5)
    private String highTitleLever;

    /** 注册证书 */
    @ApiModelProperty(value = "注册证书", position = 6)
    private String certNo;

    /** 审核人ID */
    @ApiModelProperty(value = "审核人ID", position = 7)
    private String auditorId;

    /** 审核人 */
    @ApiModelProperty(value = "审核人", position = 8)
    private String auditor;

    /** 试用期目标责任得分（40%） */
    @ApiModelProperty(value = "试用期目标责任得分（40%）", position = 9)
    private String pointScoreJson;

    /** 综合素质得分（占60%） */
    @ApiModelProperty(value = "综合素质得分（占60%）", position = 10)
    private String zhszScoreJson;

    /** 试用期部门考核评分-试用期责任得分（占40%） */
    @ApiModelProperty(value = "试用期部门考核评分-试用期责任得分（占40%）", position = 11)
    private String syzrScore;

    /** 试用期部门考核评分-综合素质得分（占60%） */
    @ApiModelProperty(value = "试用期部门考核评分-综合素质得分（占60%）", position = 12)
    private String zhszScore;

    /** 试用期部门考核评分-总分 */
    @ApiModelProperty(value = "试用期部门考核评分-总分", position = 13)
    private String totalScore;

    /** 试用期部门转正意见-优点 */
    @ApiModelProperty(value = "试用期部门转正意见-优点", position = 14)
    private String advantage;

    /** 试用期部门转正意见-不足 */
    @ApiModelProperty(value = "试用期部门转正意见-不足", position = 15)
    private String insufficient;

    /** 试用期部门转正意见-工作改进建议 */
    @ApiModelProperty(value = "试用期部门转正意见-工作改进建议", position = 16)
    private String workSuggestion;

    /** 试用期部门转正意见-考核判定结果 */
    @ApiModelProperty(value = "试用期部门转正意见-考核判定结果", position = 17)
    private String khResult;

    /** 人力资源中心意见-意见内容 */
    @ApiModelProperty(value = "人力资源中心意见-意见内容", position = 18)
    private String content;

    /** 人力资源中心意见-试用期薪资 */
    @ApiModelProperty(value = "人力资源中心意见-试用期薪资", position = 19)
    private String tryPay;

    /** 人力资源中心意见-转正后薪资 */
    @ApiModelProperty(value = "人力资源中心意见-转正后薪资", position = 20)
    private String turnPay;

    /** 人力资源中心意见-试用期职级 */
    @ApiModelProperty(value = "人力资源中心意见-试用期职级", position = 21)
    private String tryLevel;

    /** 人力资源中心意见-试用期职级 */
    @ApiModelProperty(value = "人力资源中心意见-试用期职级", position = 22)
    private String turnLevel;

    /** 人力资源中心意见-试用期转正考察审批附件 */
    @ApiModelProperty(value = "人力资源中心意见-试用期转正考察审批附件", position = 23)
    private String khspAttach;

    /** 人力资源中心意见-备注 */
    @ApiModelProperty(value = "人力资源中心意见-备注", position = 24)
    private String memo;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 25)
    private String tenantId;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 26)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 27)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 28)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 29)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 30)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 31)
    @TableField(fill = FieldFill.INSERT)
    private String dataScopeId;

    /** SN */
    @ApiModelProperty(value = "SN", position = 32)
    private String sn;

    /** 模型Id */
    @ApiModelProperty(value = "模型Id", position = 33)
    private String modelId;

    /** 流程实例Id */
    @ApiModelProperty(value = "流程实例Id", position = 34)
    private String processInstanceId;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态", position = 35)
    private String status;
}
