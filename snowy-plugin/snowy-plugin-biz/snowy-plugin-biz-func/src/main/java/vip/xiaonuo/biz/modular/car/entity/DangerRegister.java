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
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * CAR-出险登记实体
 *
 * @author yong.ye
 * @date  2023/11/08 15:49
 **/
@Getter
@Setter
@TableName("BIZ_CAR_DANGER_REGISTER")
public class DangerRegister {

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

    /** 车牌号 */
    @ApiModelProperty(value = "车牌号", position = 4)
    private String licenseNo;

    /** 出险登记人（司机） */
    @ApiModelProperty(value = "出险登记人（司机）", position = 5)
    private String optName;

    /** 登记日期（出险登记时间） */
    @ApiModelProperty(value = "登记日期（出险登记时间）", position = 6)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date optDate;

    /** 出险登记人（司机userid） */
    @ApiModelProperty(value = "出险登记人（司机userid）", position = 7)
    private String driverUserId;

    /** 事故描述 */
    @ApiModelProperty(value = "事故描述", position = 8)
    private String accidentDesc;

    /** 事故出险附件 */
    @ApiModelProperty(value = "事故出险附件", position = 9)
    private String accidentAttach;

    /** 出险-处理结果记录 */
    @ApiModelProperty(value = "出险-处理结果记录", position = 10)
    private String result;

    /** 出险-结果处理登记时间 */
    @ApiModelProperty(value = "出险-结果处理登记时间", position = 11)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date resultRegisterDate;

    /** 出险-处理结果登记人 */
    @ApiModelProperty(value = "出险-处理结果登记人", position = 12)
    private String resultRegister;

    /** 出现-处理结果附件 */
    @ApiModelProperty(value = "出现-处理结果附件", position = 13)
    private String resultAttach;

    /** 车辆Id-VehicleInformation.Id (外键) */
    @ApiModelProperty(value = "车辆Id-VehicleInformation.Id (外键)", position = 14)
    private String vehicleId;

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
}
