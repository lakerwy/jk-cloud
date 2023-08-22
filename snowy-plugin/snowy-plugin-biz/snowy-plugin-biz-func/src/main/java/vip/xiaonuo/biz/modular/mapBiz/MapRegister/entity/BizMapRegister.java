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
package vip.xiaonuo.biz.modular.mapBiz.MapRegister.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.simpleframework.xml.Transient;

import java.util.Date;

/**
 * 业务表-出图登记记录表实体
 *
 * @author mayike
 * @date  2023/07/03 11:46
 **/
@Getter
@Setter
@TableName("BIZ_MAP_REGISTER")
public class BizMapRegister {

    /** 主键id */
    @TableId
    @ApiModelProperty(value = "主键id", position = 1)
    private String id;

    /** 流程单号 */
    @ApiModelProperty(value = "流程单号", position = 2)
    private Integer taskId;

    /** 图审ID */
    @ApiModelProperty(value = "图审ID", position = 3)
    private String reviewId;

    /** 流水号 */
    @ApiModelProperty(value = "流水号", position = 4)
    private String sn;

    /** 受理人 */
    @ApiModelProperty(value = "受理人", position = 5)
    private String opt;

    /** 受理日期 */
    @ApiModelProperty(value = "受理日期", position = 6)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date optDate;

    /** 图审受理单号 */
    @ApiModelProperty(value = "图审受理单号", position = 7)
    private String crno;

    /** 设计阶段 */
    @ApiModelProperty(value = "设计阶段", position = 8)
    private String designStage;

    /** 出图盖章日期 */
    @ApiModelProperty(value = "出图盖章日期", position = 9)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date sealDate;

    /** 出图份数 */
    @ApiModelProperty(value = "出图份数", position = 10)
    private String mapCopies;

    /** 用印类型 */
    @ApiModelProperty(value = "用印类型", position = 11)
    private String useType;

    /** 印章名称 */
    @ApiModelProperty(value = "印章名称", position = 12)
    private String sealName;

    /** 是否留档 */
    @ApiModelProperty(value = "是否留档", position = 13)
    private String isFile;

    /** 是否物流 */
    @ApiModelProperty(value = "是否物流", position = 14)
    private String isLogistics;

    /** 寄件人 */
    @ApiModelProperty(value = "寄件人", position = 15)
    private String receiver;

    /** 联系方式 */
    @ApiModelProperty(value = "联系方式", position = 16)
    private String contact;

    /** 物流单号 */
    @ApiModelProperty(value = "物流单号", position = 17)
    private String logisticsNo;

    /** 收件人信息 */
    @ApiModelProperty(value = "收件人信息", position = 18)
    private String receiveinformation;

    /**  备注信息 */
    @ApiModelProperty(value = " 备注信息", position = 19)
    private String note;

    /** 用印审批单 */
    @ApiModelProperty(value = "用印审批单", position = 20)
    private String printApprovalAttachments;

    /** 图审合格证 */
    @ApiModelProperty(value = "图审合格证", position = 21)
    private String reviewQualifiedAttachments;

    /** 盖章样本 */
    @ApiModelProperty(value = "盖章样本", position = 22)
    private String stampAttachments;

    /** 项目名称 */
    @ApiModelProperty(value = "项目名称", position = 23)
    private String projectName;

    /** 项目编号 */
    @ApiModelProperty(value = "项目编号", position = 24)
    private String projectNo;

    /** 项目来源 */
    @ApiModelProperty(value = "项目来源", position = 25)
    private String companyOrDepartment;

    /** 删除状态 */
    @ApiModelProperty(value = "删除状态", position = 26)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 流程状态 */
    @ApiModelProperty(value = "流程状态", position = 27)
    private String status;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 28)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty(value = "创建人", position = 29)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间", position = 30)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty(value = "更新人", position = 31)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 模型id */
    @ApiModelProperty(value = "模型id", position = 32)
    private String modelId;

    /** 流程实例id */
    @ApiModelProperty(value = "流程实例id", position = 33)
    private String processInstanceId;

    /** 申请人 */
    @ApiModelProperty(value = "申请人", position = 34)
    private String applyUser;

    /** 申请时间 */
    @ApiModelProperty(value = "申请时间", position = 35)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date applyTime;

    @ApiModelProperty(value = "是否直接出图",position = 36)
    private String isPost;

    @TableField(exist = false)
    @ApiModelProperty(value = "审定图",position = 37)
    private String validatedAttachments;

    @ApiModelProperty(value = "物流备注", position = 38)
    private String logisticsMemo;

    @ApiModelProperty(value = "数据权限字段",position = 62)
    private String dataScopeId;
}
