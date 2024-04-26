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
package vip.xiaonuo.bp.modular.recruit.supplier.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 供应商基本信息表实体
 *
 * @author hxw
 * @date  2024/04/17 15:18
 **/
@Getter
@Setter
@TableName("BP_SUPPLIER")
public class BpSupplier {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 租户ID */
    @ApiModelProperty(value = "租户ID", position = 2)
    private String tenantId;

    /** 营业执照扫描件图像 */
    @ApiModelProperty(value = "营业执照扫描件图像", position = 3)
    private String businessLicenseImage;

    /** 法人名片图像 */
    @ApiModelProperty(value = "法人名片图像", position = 4)
    private String legalPersonImage;

    /** 法定代表人 */
    @ApiModelProperty(value = "法定代表人", position = 5)
    private String legalRepresentative;

    /** 法人身份证 */
    @ApiModelProperty(value = "法人身份证", position = 6)
    private String idNumber;

    /** 注册资本(元) */
    @ApiModelProperty(value = "注册资本(元)", position = 7)
    private BigDecimal registeredCapital;

    /** 公司logo */
    @ApiModelProperty(value = "公司logo", position = 8)
    private String companylogo;

    /** 成立日期 */
    @ApiModelProperty(value = "成立日期", position = 9)
    private Date establishTime;

    /** 公司邮箱 */
    @ApiModelProperty(value = "公司邮箱", position = 10)
    private String mailbox;

    /** 公司名称 */
    @ApiModelProperty(value = "公司名称", position = 11)
    private String companyName;

    /** 企业组织形式 */
    @ApiModelProperty(value = "企业组织形式", position = 12)
    private String companyType;

    /** 统一社会信用代码 */
    @ApiModelProperty(value = "统一社会信用代码", position = 13)
    private String corpCode;

    /** 住所省市区 */
    @ApiModelProperty(value = "住所省市区", position = 14)
    private String home;

    /** 住所详细地址 */
    @ApiModelProperty(value = "住所详细地址", position = 15)
    private String homeAddress;

    /** 办公地址省市区 */
    @ApiModelProperty(value = "办公地址省市区", position = 16)
    private String officeAddress;

    /** 办公详细地址 */
    @ApiModelProperty(value = "办公详细地址", position = 17)
    private String workAddress;

    /** 服务地址省 */
    @ApiModelProperty(value = "服务地址省", position = 18)
    private String serviceAreaDart;

    /** 服务地址市 */
    @ApiModelProperty(value = "服务地址市", position = 19)
    private String serviceAreaCity;

    /** 经营范围 */
    @ApiModelProperty(value = "经营范围", position = 20)
    private String businessScope;

    /** 来源类型 */
    @ApiModelProperty(value = "来源类型", position = 21)
    private String source;

    /** 合作承诺函 */
    @ApiModelProperty(value = "合作承诺函", position = 22)
    private String commitmentLetter;

    /** 宣读视频 */
    @ApiModelProperty(value = "宣读视频", position = 23)
    private String video;

    /** 审核状态 */
    @ApiModelProperty(value = "审核状态", position = 24)
    private String status;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 25)
    private String memo;

    /** 黑名单标记 */
    @ApiModelProperty(value = "黑名单标记", position = 26)
    private String isBlacklist;

    /** 黑名单原因 */
    @ApiModelProperty(value = "黑名单原因", position = 27)
    private String blacklistReason;

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

    /** 删除状态标记 */
    @ApiModelProperty(value = "删除状态标记", position = 32)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 一级分类ID */
    @ApiModelProperty(value = "一级分类ID", position = 33)
    @TableField(exist = false)
    private String stairclassifyId;

    /** 二级分类ID */
    @ApiModelProperty(value = "二级分类ID", position = 34)
    @TableField(exist = false)
    private String accessclassifyId;

    /** 三级分类ID */
    @ApiModelProperty(value = "三级分类ID", position = 35)
    @TableField(exist = false)
    private String reclassifyId;
}
