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
 * BIZ_CAR_VEHICLE_INFORMATION添加参数
 *
 * @author yong.ye
 * @date  2023/11/08 17:17
 **/
@Getter
@Setter
public class VehicleInformationAddParam {

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

    /** 车辆品牌 */
    @ApiModelProperty(value = "车辆品牌", position = 7)
    private String vehicleBrand;

    /** 车辆类型 */
    @ApiModelProperty(value = "车辆类型", position = 8)
    private String vehicleType;

    /** 乘坐人数 */
    @ApiModelProperty(value = "乘坐人数", position = 9)
    private String seats;

    /** 排量 */
    @ApiModelProperty(value = "排量", position = 10)
    private BigDecimal cc;

    /** 车辆颜色 */
    @ApiModelProperty(value = "车辆颜色", position = 11)
    private String color;

    /** 购置日期 */
    @ApiModelProperty(value = "购置日期", position = 12)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date purchaseTime;

    /** 购置价格 */
    @ApiModelProperty(value = "购置价格", position = 13)
    private BigDecimal purchasePrice;

    /** 适用燃油标号 92 95 98 */
    @ApiModelProperty(value = "适用燃油标号 92 95 98", position = 14)
    private String fuel;

    /** 油卡号码 */
    @ApiModelProperty(value = "油卡号码", position = 15)
    private String oilCardNumber;

    /** 总行驶里程（KM） */
    @ApiModelProperty(value = "总行驶里程（KM）", position = 16)
    private BigDecimal totalMileage;

    /** 车辆状态 可用 出车 维保 其他 */
    @ApiModelProperty(value = "车辆状态 可用 出车 维保 其他", position = 17)
    private String status;

    /** 参考保养里程（KM） */
    @ApiModelProperty(value = "参考保养里程（KM）", position = 18)
    private BigDecimal referenceMileage;

    /** 参考保养周期（月） */
    @ApiModelProperty(value = "参考保养周期（月）", position = 19)
    private Double referencePeriod;

    /** 上次保养里程（KM） */
    @ApiModelProperty(value = "上次保养里程（KM）", position = 20)
    private BigDecimal lastMileage;

    /** 上次保养日期 */
    @ApiModelProperty(value = "上次保养日期", position = 21)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date maintenanceDate;

    /** 照片 */
    @ApiModelProperty(value = "照片", position = 22)
    private String photo;

    /** 行驶证有效期 */
    @ApiModelProperty(value = "行驶证有效期", position = 23)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date licenseValid;

    /** 扫描件 */
    @ApiModelProperty(value = "扫描件", position = 24)
    private String scanningCopy;

    /** 交强险到期日（即 保险到期日） */
    @ApiModelProperty(value = "交强险到期日（即 保险到期日）", position = 25)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date expireDate1;

    /** 电子保单 */
    @ApiModelProperty(value = "电子保单", position = 26)
    private String attachment1;

    /** 商业险到期日 */
    @ApiModelProperty(value = "商业险到期日", position = 27)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date expireDate2;

    /** 电子保单 */
    @ApiModelProperty(value = "电子保单", position = 28)
    private String attachments2;

    /** 年审到期日 */
    @ApiModelProperty(value = "年审到期日", position = 29)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date yearAuditDate;

    /** 总计加油量 */
    @ApiModelProperty(value = "总计加油量", position = 30)
    private BigDecimal totalOilFuel;

    /** 用车类型(公用|私用) */
    @ApiModelProperty(value = "用车类型(公用|私用)", position = 40)
    private String useType;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 41)
    private String memo;

}
