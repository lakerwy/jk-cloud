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
package vip.xiaonuo.biz.modular.cad.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * CAD-提资|校审|成品文件存储目录结构实体
 *
 * @author yong.ye
 * @date  2023/12/12 15:14
 **/
@Getter
@Setter
@TableName("BIZ_CAD_STORAGE")
public class CadStorage {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 租户Id */
    @ApiModelProperty(value = "租户Id", position = 2)
    private String tenantId;

    /** 父文件Id */
    @ApiModelProperty(value = "父文件Id", position = 3)
    private String parentId;

    /** 名称 */
    @ApiModelProperty(value = "名称", position = 4)
    private String name;

    /** 类型:文件夹|文件 */
    @ApiModelProperty(value = "类型:文件夹|文件", position = 5)
    private String type;

    /** 外键：项目Id -> BIZ_CAD_PROJECT.ID */
    @ApiModelProperty(value = "外键：项目Id -> BIZ_CAD_PROJECT.ID", position = 6)
    private String prjId;

    /** 外键：阶段Id -> BIZ_CAD_STAGE.ID */
    @ApiModelProperty(value = "外键：阶段Id -> BIZ_CAD_STAGE.ID", position = 7)
    private String stageId;

    /** 外键：子项Id -> BIZ_CAD_SUB_ITEM.ID */
    @ApiModelProperty(value = "外键：子项Id -> BIZ_CAD_SUB_ITEM.ID", position = 8)
    private String subItemId;

    /** 外键：cad专业Id -> BIZ_CAD_PROFESSION.ID */
    @ApiModelProperty(value = "外键：cad专业Id -> BIZ_CAD_PROFESSION.ID", position = 9)
    private String professionId;

    /** 目录来源(文件Id)：BIZ_CAD_FILE.Id */
    @ApiModelProperty(value = "目录来源(文件Id)：BIZ_CAD_FILE.Id", position = 10)
    private String oldFId;

    /** 目录来源(文件父Id)：BIZ_CAD_FILE.ParentId */
    @ApiModelProperty(value = "目录来源(文件父Id)：BIZ_CAD_FILE.ParentId", position = 11)
    private String oldFParentId;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 12)
    private String memo;

    /** 删除状态标记 */
    @ApiModelProperty(value = "删除状态标记", position = 13)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 14)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty(value = "创建人", position = 15)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间", position = 16)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty(value = "更新人", position = 17)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;
}
