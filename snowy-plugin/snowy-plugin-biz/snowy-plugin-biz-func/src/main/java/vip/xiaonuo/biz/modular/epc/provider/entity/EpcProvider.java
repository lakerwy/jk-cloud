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
package vip.xiaonuo.biz.modular.epc.provider.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * EPC-供应商库表实体
 *
 * @author yong.ye
 * @date  2023/09/05 14:57
 **/
@Getter
@Setter
@TableName("EPC_PROVIDER")
public class EpcProvider {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 租户Id */
    @ApiModelProperty(value = "租户Id", position = 2)
    private String tenantId;

    /** 供应商类别 */
    @ApiModelProperty(value = "供应商类别", position = 3)
    private String prvType;

    /** 公司名称 */
    @ApiModelProperty(value = "公司名称", position = 4)
    private String prvName;

    /** 统一社会信用代码 */
    @ApiModelProperty(value = "统一社会信用代码", position = 5)
    private String socialCode;

    /** 法定代表人 */
    @ApiModelProperty(value = "法定代表人", position = 6)
    private String legaler;

    /** 法定代表人电话 */
    @ApiModelProperty(value = "法定代表人电话", position = 7)
    private String phone;

    /** 注册资本 */
    @ApiModelProperty(value = "注册资本", position = 8)
    private String signCapital;

    /** 注册地址 */
    @ApiModelProperty(value = "注册地址", position = 9)
    private String signAddress;

    /** 办公地址 */
    @ApiModelProperty(value = "办公地址", position = 10)
    private String workAddress;

    /** 营业执照 */
    @ApiModelProperty(value = "营业执照", position = 11)
    private String businessLicense;

    /** 实际控制人 */
    @ApiModelProperty(value = "实际控制人", position = 12)
    private String realController;

    /** （实际控制人）联系电话 */
    @ApiModelProperty(value = "（实际控制人）联系电话", position = 13)
    private String realControllerPhone;

    /** 对接人 */
    @ApiModelProperty(value = "对接人", position = 14)
    private String contactor;

    /** （对接人）联系电话 */
    @ApiModelProperty(value = "（对接人）联系电话", position = 15)
    private String contactorPhone;

    /** 安全生产许可证 */
    @ApiModelProperty(value = "安全生产许可证", position = 16)
    private String safeLicense;

    /** 供应商来源 */
    @ApiModelProperty(value = "供应商来源", position = 17)
    private String prvResource;

    /** 货物类别 */
    @ApiModelProperty(value = "货物类别", position = 18)
    private String cargoType;

    /** 资质等级 */
    @ApiModelProperty(value = "资质等级", position = 19)
    private String qualityLevel;

    /** 专业类别 */
    @ApiModelProperty(value = "专业类别", position = 20)
    private String majorType;

    /** 资质证书 */
    @ApiModelProperty(value = "资质证书", position = 21)
    private String qualityLicense;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 22)
    private String memo;

    /** 删除状态标记 */
    @ApiModelProperty(value = "删除状态标记", position = 23)
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
}
