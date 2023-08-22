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
package vip.xiaonuo.biz.modular.mapBiz.Chartreviews.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 业务表-图审受理单
实体
 *
 * @author mayike
 * @date  2023/06/30 16:07
 **/
@Getter
@Setter
@TableName("BIZ_CHART_REVIEWS")
public class BizChartReviews {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 流程单号 */
    @ApiModelProperty(value = "流程单号", position = 2)
    private Integer taskId;

    /** SN值 */
    @ApiModelProperty(value = "SN值", position = 3)
    private String sn;

    /** 受理人 */
    @ApiModelProperty(value = "受理人", position = 4)
    private String opt;

    /** 受理时间 */
    @ApiModelProperty(value = "受理时间", position = 5)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date optDate;

    /** 项目(合同)编号 */
    @ApiModelProperty(value = "项目(合同)编号", position = 6)
    private String projectNo;

    /** 送审人 */
    @ApiModelProperty(value = "送审人", position = 7)
    private String sender;

    /** 项目来源 */
    @ApiModelProperty(value = "项目来源", position = 8)
    private String companyOrDepartment;

    /** 项目负责人 */
    @ApiModelProperty(value = "项目负责人", position = 9)
    private String projectLeader;

    /** 设计阶段 */
    @ApiModelProperty(value = "设计阶段", position = 10)
    private String designStage;

    /** 设计人 */
    @ApiModelProperty(value = "设计人", position = 11)
    private String designer;

    /** 初次出图日期 */
    @ApiModelProperty(value = "初次出图日期", position = 12)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date mapDate;

    /** 初次出图份数 */
    @ApiModelProperty(value = "初次出图份数", position = 13)
    private String mapCopies;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 14)
    private String note;

    /** 送审图 */
    @ApiModelProperty(value = "送审图", position = 15)
    private String reviewAttachments;

    /** 暂时未用到 */
    @ApiModelProperty(value = "暂时未用到", position = 16)
    private String attachments1;

    /** 审定图 */
    @ApiModelProperty(value = "审定图", position = 17)
    private String validatedAttachments;

    /** 其他资料 */
    @ApiModelProperty(value = "其他资料", position = 18)
    private String otherAttachments;

    /** 盖章日期 */
    @ApiModelProperty(value = "盖章日期", position = 19)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date sealDate;

    /** 是否留档 */
    @ApiModelProperty(value = "是否留档", position = 20)
    private String isFile;

    /** 是否物流 */
    @ApiModelProperty(value = "是否物流", position = 21)
    private String isLogistics;

    /** 受理状态 */
    @ApiModelProperty(value = "受理状态", position = 22)
    private String status;

    /** 删除状态 */
    @ApiModelProperty(value = "删除状态", position = 23)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 24)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty(value = "创建人", position = 25)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间", position = 26)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty(value = "更新人", position = 27)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    @ApiModelProperty(value = "数据权限字段",position = 62)
    private String dataScopeId;
}
