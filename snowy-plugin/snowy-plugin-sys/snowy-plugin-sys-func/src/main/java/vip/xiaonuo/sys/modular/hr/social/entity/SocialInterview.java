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
package vip.xiaonuo.sys.modular.hr.social.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 社招 - （应聘者）- 面试入职流程实体
 *
 * @author yong.ye
 * @date  2024/07/01 09:36
 **/
@Getter
@Setter
@TableName("HR_SOCIAL_INTERVIEW")
public class SocialInterview {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 应聘部门Id */
    @ApiModelProperty(value = "应聘部门Id", position = 2)
    private String deptId;

    /** 应聘部门 */
    @ApiModelProperty(value = "应聘部门", position = 3)
    private String dept;

    /** 应聘岗位 */
    @ApiModelProperty(value = "应聘岗位", position = 4)
    private String position;

    /** 外键：社招应聘者简历Id */
    @ApiModelProperty(value = "外键：社招应聘者简历Id", position = 5)
    private String hrSocialResumeId;

    /** 匹配岗位说明书任职资格要求JSON */
    @ApiModelProperty(value = "匹配岗位说明书任职资格要求JSON", position = 6)
    private String matchJson;

    /** 人力资源意见JSON */
    @ApiModelProperty(value = "人力资源意见JSON", position = 7)
    private String hrJson;

    /** 用人部门意见JSON */
    @ApiModelProperty(value = "用人部门意见JSON", position = 8)
    private String deptJson;

    /** 建议薪酬JSON */
    @ApiModelProperty(value = "建议薪酬JSON", position = 9)
    private String salaryJson;

    /** 董事长意见JSON */
    @ApiModelProperty(value = "董事长意见JSON", position = 10)
    private String bossJson;

    /** 录用结果 */
    @ApiModelProperty(value = "录用结果", position = 11)
    private String reasult;

    /** 其他附件 */
    @ApiModelProperty(value = "其他附件", position = 12)
    private String otherAttach;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 13)
    private String memo;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 14)
    private String tenantId;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 15)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 16)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 17)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 18)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 19)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 数据权限控制字段 */
    @ApiModelProperty(value = "数据权限控制字段", position = 20)
    @TableField(fill = FieldFill.INSERT)
    private String dataScopeId;

    /** SN */
    @ApiModelProperty(value = "SN", position = 21)
    private String sn;

    /** 模型Id */
    @ApiModelProperty(value = "模型Id", position = 22)
    private String modelId;

    /** 流程实例Id */
    @ApiModelProperty(value = "流程实例Id", position = 23)
    private String processInstanceId;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态", position = 24)
    private String status;
}
