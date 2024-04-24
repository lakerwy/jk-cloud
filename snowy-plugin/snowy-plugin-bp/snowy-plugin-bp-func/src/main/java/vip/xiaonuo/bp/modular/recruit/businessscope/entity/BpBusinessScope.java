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
package vip.xiaonuo.bp.modular.recruit.businessscope.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 经营范围分类信息表实体
 *
 * @author hxw
 * @date  2024/04/17 15:26
 **/
@Getter
@Setter
@TableName("BP_BUSINESS_SCOPE")
public class BpBusinessScope {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 一级分类ID */
    @ApiModelProperty(value = "一级分类ID", position = 2)
    private String stairclassifyId;

    /** 一级分类名称 */
    @ApiModelProperty(value = "一级分类名称", position = 3)
    private String stairclassifyName;

    /** 二级分类ID */
    @ApiModelProperty(value = "二级分类ID", position = 4)
    private String accessclassifyId;

    /** 二级分类名称 */
    @ApiModelProperty(value = "二级分类名称", position = 5)
    private String accessclassifyName;

    /** 三级分类ID */
    @ApiModelProperty(value = "三级分类ID", position = 6)
    private String reclassifyId;

    /** 三级分类名称 */
    @ApiModelProperty(value = "三级分类名称", position = 7)
    private String reclassifyName;

    /** 供应商ID */
    @ApiModelProperty(value = "供应商ID", position = 8)
    private String supplierId;

    /** 租户ID */
    @ApiModelProperty(value = "租户ID", position = 9)
    private String tenantId;

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

    /** 删除状态标记 */
    @ApiModelProperty(value = "删除状态标记", position = 14)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;
}
