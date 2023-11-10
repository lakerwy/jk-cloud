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
 * CAR-加油记录实体
 *
 * @author yong.ye
 * @date  2023/11/08 16:01
 **/
@Getter
@Setter
@TableName("BIZ_CAR_FUEL_REGISTER")
public class FuelRegister {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 租户Id */
    @ApiModelProperty(value = "租户Id", position = 2)
    private String tenantId;

    /** SN */
    @ApiModelProperty(value = "SN", position = 3)
    private String sn;

    /** 登记人 */
    @ApiModelProperty(value = "登记人", position = 4)
    private String opt;

    /** 登记日期 */
    @ApiModelProperty(value = "登记日期", position = 5)
    private Date optDate;

    /** 车牌号码 */
    @ApiModelProperty(value = "车牌号码", position = 6)
    private String licenseNo;

    /** 加油时间 */
    @ApiModelProperty(value = "加油时间", position = 7)
    private Date refuelDate;

    /** 经办人 */
    @ApiModelProperty(value = "经办人", position = 8)
    private String agent;

    /** 油卡号码 */
    @ApiModelProperty(value = "油卡号码", position = 9)
    private String oilCard;

    /** 燃油标号 */
    @ApiModelProperty(value = "燃油标号", position = 10)
    private String fuelLable;

    /** 油品单价（元/L） */
    @ApiModelProperty(value = "油品单价（元/L）", position = 11)
    private BigDecimal oilPrice;

    /** 加油量 */
    @ApiModelProperty(value = "加油量", position = 12)
    private BigDecimal fuelCharge;

    /** 加油金额（元） */
    @ApiModelProperty(value = "加油金额（元）", position = 13)
    private BigDecimal amount;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 14)
    private String notes;

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

    /** 附件Id */
    @ApiModelProperty(value = "附件Id", position = 20)
    private String attachId;

    /** 车辆Id */
    @ApiModelProperty(value = "车辆Id", position = 21)
    private String vehicleId;
}
