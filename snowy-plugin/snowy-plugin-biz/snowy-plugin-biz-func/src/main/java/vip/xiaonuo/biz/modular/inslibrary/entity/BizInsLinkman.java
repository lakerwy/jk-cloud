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
 * 业务表-产业生态链-资源库-联络人实体
 *
 * @author yong.ye
 * @date  2023/08/15 15:27
 **/
@Getter
@Setter
@TableName("BIZ_INS_LINKMAN")
public class BizInsLinkman {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 外键，资源库主键ID */
    @ApiModelProperty(value = "外键，资源库主键ID", position = 2)
    private String libraryId;

    /** 姓名 */
    @ApiModelProperty(value = "姓名", position = 3)
    private String name;

    /** 性别 */
    @ApiModelProperty(value = "性别", position = 4)
    private String sex;

    /** 职务 */
    @ApiModelProperty(value = "职务", position = 5)
    private String duty;

    /** 生日 */
    @ApiModelProperty(value = "生日", position = 6)
    private Date birthday;

    /** 联系方式 */
    @ApiModelProperty(value = "联系方式", position = 7)
    private String phone;

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
}
