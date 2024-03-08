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
package vip.xiaonuo.dev.modular.userRelation.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目系统和账户的关联关系表实体
 *
 * @author mayike
 * @date  2024/02/29 11:22
 **/
@Getter
@Setter
@TableName("SYSTEM_USER_RELATION")
public class SystemUserRelation {

    /** 主键 */
    @TableId
    @ApiModelProperty(value = "主键", position = 1)
    private String id;

    /** 租户ID */
    @ApiModelProperty(value = "租户ID", position = 2)
    private String tenantId;

    /** 项目系统ID */
    @ApiModelProperty(value = "项目系统ID", position = 3)
    private String systemId;

    /** 用户ID */
    @ApiModelProperty(value = "用户ID", position = 4)
    private String userId;

    /** 用户账号 */
    @ApiModelProperty(value = "用户账号", position = 5)
    private String userAccount;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 6)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 7)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 8)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 9)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 10)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    @ApiModelProperty(value = "角色key",position = 11)
    private String roleKey;

    @ApiModelProperty(value = "用户姓名",position = 12)
    @TableField(exist = false)
    private String userName;
}
