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
package vip.xiaonuo.biz.modular.zizhi.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 业务表-资质-企业资质证书表实体
 *
 * @author yong.ye
 * @date  2023/09/01 09:56
 **/
@Getter
@Setter
@TableName("BIZ_ZIZHI_CERTIFICATE")
public class BizZizhiCertificate {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 流水号 */
    @ApiModelProperty(value = "流水号", position = 2)
    private String sn;

    /** 公司名称 */
    @ApiModelProperty(value = "公司名称", position = 3)
    private String company;

    /** 资质类别 */
    @ApiModelProperty(value = "资质类别", position = 4)
    private String type;

    /** 获取时间 */
    @ApiModelProperty(value = "获取时间", position = 5)
    private String gainDate;

    /** 资质名称 */
    @ApiModelProperty(value = "资质名称", position = 6)
    private String ziZhiName;

    /** 颁发机构 */
    @ApiModelProperty(value = "颁发机构", position = 7)
    private String issueOrg;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 8)
    private String memo;

    /** 删除状态标记 */
    @ApiModelProperty(value = "删除状态标记", position = 9)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 10)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty(value = "创建人", position = 11)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间", position = 12)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty(value = "更新人", position = 13)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 证书编号 */
    @ApiModelProperty(value = "证书编号", position = 14)
    private String certNo;

    /** 有效期 */
    @ApiModelProperty(value = "有效期", position = 15)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @TableField(insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private Date validity;

    /** 存管单位 */
    @ApiModelProperty(value = "存管单位", position = 16)
    private String unit;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 17)
    private String attachment;
}
