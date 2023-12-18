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
package vip.xiaonuo.biz.modular.keyProject.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * BIZ-重点项目信息实体
 *
 * @author yong.ye
 * @date  2023/12/18 15:29
 **/
@Getter
@Setter
@TableName("BIZ_KEY_PROJECT")
public class BizKeyProject {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 租户Id */
    @ApiModelProperty(value = "租户Id", position = 2)
    private String tenantId;

    /** 项目名称 */
    @ApiModelProperty(value = "项目名称", position = 3)
    private String name;

    /** 项目规模 */
    @ApiModelProperty(value = "项目规模", position = 4)
    private String scale;

    /** 投标限价 */
    @ApiModelProperty(value = "投标限价", position = 5)
    private String bidLimit;

    /** 资格要求 */
    @ApiModelProperty(value = "资格要求", position = 6)
    private String requirement;

    /** 联合体单位名称 */
    @ApiModelProperty(value = "联合体单位名称", position = 7)
    private String unitName;

    /** 分值权重-技能 */
    @ApiModelProperty(value = "分值权重-技能", position = 8)
    private String scoreSkill;

    /** 分值权重-商务 */
    @ApiModelProperty(value = "分值权重-商务", position = 9)
    private String scoreBusiness;

    /** 分值权重-报价 */
    @ApiModelProperty(value = "分值权重-报价", position = 10)
    private String scorePrice;

    /** 评价方法 */
    @ApiModelProperty(value = "评价方法", position = 11)
    private String evaluationMethod;

    /** 扣分情况 */
    @ApiModelProperty(value = "扣分情况", position = 12)
    private String deduction;

    /** 报价得分计算方法 */
    @ApiModelProperty(value = "报价得分计算方法", position = 13)
    private String scoreMethod;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 14)
    private String memo;

    /** 招标文件上传 */
    @ApiModelProperty(value = "招标文件上传", position = 15)
    private String attach;

    /** 流水号 */
    @ApiModelProperty(value = "流水号", position = 16)
    private String sn;

    /** 项目类型 */
    @ApiModelProperty(value = "项目类型", position = 17)
    private String type;

    /** 开标时间 */
    @ApiModelProperty(value = "开标时间", position = 18)
    @TableField(insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date bidTime;

    /** 对接人 */
    @ApiModelProperty(value = "对接人", position = 19)
    private String contact;

    /** 删除状态标记 */
    @ApiModelProperty(value = "删除状态标记", position = 20)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 21)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty(value = "创建人", position = 22)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间", position = 23)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty(value = "更新人", position = 24)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;
}
