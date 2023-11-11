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
package vip.xiaonuo.biz.modular.car.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * CAR-加油记录添加参数
 *
 * @author yong.ye
 * @date  2023/11/08 16:01
 **/
@Getter
@Setter
public class FuelRegisterAddParam {

    /** SN */
    @ApiModelProperty(value = "SN", position = 3)
    private String sn;

    /** 登记人 */
    @ApiModelProperty(value = "登记人", position = 4)
    private String opt;

    /** 登记日期 */
    @ApiModelProperty(value = "登记日期", position = 5)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date optDate;

    /** 车牌号码 */
    @ApiModelProperty(value = "车牌号码", position = 6)
    private String licenseNo;

    /** 加油时间 */
    @ApiModelProperty(value = "加油时间", position = 7)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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

    /** 附件Id */
    @ApiModelProperty(value = "附件Id", position = 20)
    private String attachId;

    /** 车辆Id */
    @ApiModelProperty(value = "车辆Id", position = 21)
    private String vehicleId;

}
