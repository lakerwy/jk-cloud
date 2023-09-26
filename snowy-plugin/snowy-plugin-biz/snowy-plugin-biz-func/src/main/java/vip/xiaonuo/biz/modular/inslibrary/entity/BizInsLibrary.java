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
package vip.xiaonuo.biz.modular.inslibrary.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 业务表-产业生态链-资源库实体
 *
 * @author yong.ye
 * @date  2023/08/15 15:23
 **/
@Getter
@Setter
@TableName("BIZ_INS_LIBRARY")
public class BizInsLibrary {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 所属行业门类 */
    @ApiModelProperty(value = "所属行业门类", position = 2)
    private String industryCategory;

    /** 所属行业大类 */
    @ApiModelProperty(value = "所属行业大类", position = 3)
    private String subCategory;

    /** 企业名称 */
    @ApiModelProperty(value = "企业名称", position = 4)
    private String enterpriseName;

    /** 组织机构代码 */
    @ApiModelProperty(value = "组织机构代码", position = 5)
    private String orgCode;

    /** 企业地址 */
    @ApiModelProperty(value = "企业地址", position = 6)
    private String address;

    /** 关系类型 */
    @ApiModelProperty(value = "关系类型", position = 7)
    private String relationType;

    /** 建科联系人 */
    @ApiModelProperty(value = "建科联系人", position = 8)
    private String jkContactor;

    /** 部门 */
    @ApiModelProperty(value = "部门", position = 9)
    private String jkDept;

    /** 联系方式 */
    @ApiModelProperty(value = "联系方式", position = 10)
    private String phone;

    /** 主要特色 */
    @ApiModelProperty(value = "主要特色", position = 11)
    private String mainFeatures;

    /** 与我司互补说明 */
    @ApiModelProperty(value = "与我司互补说明", position = 12)
    private String explainMemo;

    /** 与我司是否合作 */
    @ApiModelProperty(value = "与我司是否合作", position = 13)
    private String isHz;

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
}
