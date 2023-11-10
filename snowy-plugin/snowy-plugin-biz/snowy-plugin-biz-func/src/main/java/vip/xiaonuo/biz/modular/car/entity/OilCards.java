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
package vip.xiaonuo.biz.modular.car.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * CAR-油卡信息实体
 *
 * @author yong.ye
 * @date  2023/11/08 16:01
 **/
@Getter
@Setter
@TableName("BIZ_CAR_OIL_CARDS")
public class OilCards {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 租户Id */
    @ApiModelProperty(value = "租户Id", position = 2)
    private String tenantId;

    /** 卡号 */
    @ApiModelProperty(value = "卡号", position = 3)
    private String cardNumber;

    /** 发卡日期 */
    @ApiModelProperty(value = "发卡日期", position = 4)
    private Date issuingDate;

    /** 渠道 */
    @ApiModelProperty(value = "渠道", position = 5)
    private String channel;

    /** 余额 */
    @ApiModelProperty(value = "余额", position = 6)
    private BigDecimal balance;

    /** 删除状态标记 */
    @ApiModelProperty(value = "删除状态标记", position = 7)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 8)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty(value = "创建人", position = 9)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间", position = 10)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty(value = "更新人", position = 11)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;
}
