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
 * CAR-费用管理编辑参数
 *
 * @author yong.ye
 * @date  2023/11/08 16:06
 **/
@Getter
@Setter
public class VehicleFeesEditParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true, position = 1)
    @NotBlank(message = "id不能为空")
    private String id;

    /** 车辆Id */
    @ApiModelProperty(value = "车辆Id", position = 3)
    private String vehicleId;

    /** SN */
    @ApiModelProperty(value = "SN", position = 4)
    private String sn;

    /** 登记人 */
    @ApiModelProperty(value = "登记人", position = 5)
    private String opt;

    /** 登记日期 */
    @ApiModelProperty(value = "登记日期", position = 6)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date optDate;

    /** 车牌号码 */
    @ApiModelProperty(value = "车牌号码", position = 7)
    private String licenseNo;

    /** 费用类别 */
    @ApiModelProperty(value = "费用类别", position = 8)
    private String type;

    /** 经办人 */
    @ApiModelProperty(value = "经办人", position = 9)
    private String operator;

    /** 费用日期 */
    @ApiModelProperty(value = "费用日期", position = 10)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date chargeDate;

    /** 维修单位 */
    @ApiModelProperty(value = "维修单位", position = 11)
    private String maintenanceUnit;

    /** 维修费用 */
    @ApiModelProperty(value = "维修费用", position = 12)
    private BigDecimal fee;

    /** 止码 */
    @ApiModelProperty(value = "止码", position = 13)
    private String checkCode;

    /** 维修内容 */
    @ApiModelProperty(value = "维修内容", position = 14)
    private String maintenanceContents;

    /** 保养单位 */
    @ApiModelProperty(value = "保养单位", position = 15)
    private String unkeepUnit;

    /** 保养费用 */
    @ApiModelProperty(value = "保养费用", position = 16)
    private BigDecimal unkeepFee;

    /** 止码(新增保养记录时：当前里程 => （同步至）车辆的上次保养里程) */
    @ApiModelProperty(value = "止码(新增保养记录时：当前里程 => （同步至）车辆的上次保养里程)", position = 17)
    private String checkCode1;

    /** 保养内容 */
    @ApiModelProperty(value = "保养内容", position = 18)
    private String unkeepContents;

    /** 承保单位 */
    @ApiModelProperty(value = "承保单位", position = 19)
    private String insuredUnit;

    /** 保险费用 */
    @ApiModelProperty(value = "保险费用", position = 20)
    private BigDecimal insuranceFee;

    /** 到期日期 */
    @ApiModelProperty(value = "到期日期", position = 21)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dueTime;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 22)
    private String note;

    /** 年审单位 */
    @ApiModelProperty(value = "年审单位", position = 23)
    private String annualUnit;

    /** 年审费用 */
    @ApiModelProperty(value = "年审费用", position = 24)
    private BigDecimal annualFee;

    /** 备注（年审） */
    @ApiModelProperty(value = "备注（年审）", position = 25)
    private String note1;

    /** 停车场 */
    @ApiModelProperty(value = "停车场", position = 26)
    private String park;

    /** 停车费用 */
    @ApiModelProperty(value = "停车费用", position = 27)
    private BigDecimal parkingFee;

    /** 缴费方式 */
    @ApiModelProperty(value = "缴费方式", position = 28)
    private String payMethod;

    /** 费用期间 */
    @ApiModelProperty(value = "费用期间", position = 29)
    private String feePeriod;

    /** 备注（停车） */
    @ApiModelProperty(value = "备注（停车）", position = 30)
    private String note2;

    /** 过路费 */
    @ApiModelProperty(value = "过路费", position = 31)
    private String roadToll;

    /** 费用 */
    @ApiModelProperty(value = "费用", position = 32)
    private BigDecimal roadFee;

    /** 缴费方式 */
    @ApiModelProperty(value = "缴费方式", position = 33)
    private String feePeriod1;

    /** 备注（过路） */
    @ApiModelProperty(value = "备注（过路）", position = 34)
    private String note3;

    /** 费用名称 */
    @ApiModelProperty(value = "费用名称", position = 35)
    private String feeName;

    /** 金额 */
    @ApiModelProperty(value = "金额", position = 36)
    private BigDecimal amount;

    /** 缴费方式 */
    @ApiModelProperty(value = "缴费方式", position = 37)
    private String feePeriod2;

    /** 备注（费用） */
    @ApiModelProperty(value = "备注（费用）", position = 38)
    private String note4;

    /** 附件 */
    @ApiModelProperty(value = "附件", position = 39)
    private String attachments;

}
