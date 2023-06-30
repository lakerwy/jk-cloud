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
package vip.xiaonuo.biz.modular.mapBiz.AuditLists.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 业务表-设计校审主表编辑参数
 *
 * @author mayike
 * @date  2023/06/30 16:43
 **/
@Getter
@Setter
public class BizAuditListsEditParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 流程单号 */
    @ApiModelProperty(value = "流程单号", position = 2)
    private Integer taskId;

    /** 图审ID */
    @ApiModelProperty(value = "图审ID", position = 3)
    private Integer reviewId;

    /** SN单号 */
    @ApiModelProperty(value = "SN单号", position = 4)
    private String sn;

    /** 项目名称 */
    @ApiModelProperty(value = "项目名称", position = 5)
    private String projectName;

    /** 图审受理号 */
    @ApiModelProperty(value = "图审受理号", position = 6)
    private String crNo;

    /** 项目来源 */
    @ApiModelProperty(value = "项目来源", position = 7)
    private String companyOrDepartment;

    /** 设计阶段 */
    @ApiModelProperty(value = "设计阶段", position = 8)
    private String designStage;

    /** 设计人 */
    @ApiModelProperty(value = "设计人", position = 9)
    private String designer;

    /** 校审专业 */
    @ApiModelProperty(value = "校审专业", position = 10)
    private String profession;

    /** 校审人 */
    @ApiModelProperty(value = "校审人", position = 11)
    private String reader;

    /** 审核等级 */
    @ApiModelProperty(value = "审核等级", position = 12)
    private String grade;

    /** 送审图 */
    @ApiModelProperty(value = "送审图", position = 13)
    private String attachments;

    /** 提交人 */
    @ApiModelProperty(value = "提交人", position = 14)
    private String opt;

    /** 提交时间 */
    @ApiModelProperty(value = "提交时间", position = 15)
    private Date optDate;

    /** ATTACHMENT1 */
    @ApiModelProperty(value = "ATTACHMENT1", position = 16)
    private String attachment1;

    /** 状态 */
    @ApiModelProperty(value = "状态", position = 17)
    private String status;

    /** 审订次数 */
    @ApiModelProperty(value = "审订次数", position = 18)
    private Integer counts;

    /** TIMES */
    @ApiModelProperty(value = "TIMES", position = 19)
    private Integer times;

    /** OPERATE_TIME */
    @ApiModelProperty(value = "OPERATE_TIME", position = 20)
    private Date operateTime;

    /** 模型id */
    @ApiModelProperty(value = "模型id", position = 26)
    private String modelId;

    /** 流程实例id */
    @ApiModelProperty(value = "流程实例id", position = 27)
    private String processInstanceId;

    /** 申请人 */
    @ApiModelProperty(value = "申请人", position = 28)
    private String applyUser;

    /** 申请时间 */
    @ApiModelProperty(value = "申请时间", position = 29)
    private Date applyTime;

}
