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
package vip.xiaonuo.bp.modular.platform.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 招采管理 - 操作指引实体
 *
 * @author yong.ye
 * @date  2024/05/29 16:22
 **/
@Getter
@Setter
@TableName("BP_POINT")
public class BpPoint {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 标题 */
    @ApiModelProperty(value = "标题", position = 2)
    private String title;

    /** 正文内容 */
    @ApiModelProperty(value = "正文内容", position = 3)
    private String content;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 4)
    private String attach;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 5)
    private String memo;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 6)
    private String tenantId;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 7)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 8)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 9)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 10)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 11)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;
}
