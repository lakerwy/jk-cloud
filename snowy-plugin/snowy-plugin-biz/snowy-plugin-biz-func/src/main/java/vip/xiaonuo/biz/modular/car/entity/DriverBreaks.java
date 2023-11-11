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
 * CAR-违章记录实体
 *
 * @author yong.ye
 * @date  2023/11/08 15:58
 **/
@Getter
@Setter
@TableName("BIZ_CAR_DRIVER_BREAKS")
public class DriverBreaks {

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

    /** 司机姓名 */
    @ApiModelProperty(value = "司机姓名", position = 4)
    private String name;

    /** 违章车辆 */
    @ApiModelProperty(value = "违章车辆", position = 5)
    private String vehicle;

    /** 违章时间 */
    @ApiModelProperty(value = "违章时间", position = 6)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date breakTime;

    /** 罚款金额 */
    @ApiModelProperty(value = "罚款金额", position = 7)
    private BigDecimal forfeit;

    /** 决定书编号 */
    @ApiModelProperty(value = "决定书编号", position = 8)
    private String decisionNo;

    /** 记分 */
    @ApiModelProperty(value = "记分", position = 9)
    private Integer score;

    /** 状态 */
    @ApiModelProperty(value = "状态", position = 10)
    private String status;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 11)
    private String note;

    /** 删除状态标记 */
    @ApiModelProperty(value = "删除状态标记", position = 12)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 13)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty(value = "创建人", position = 14)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间", position = 15)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty(value = "更新人", position = 16)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 司机UserId */
    @ApiModelProperty(value = "司机UserId", position = 17)
    private String driverUserId;

    /** 附件Id */
    @ApiModelProperty(value = "附件Id", position = 18)
    private String attachId;

    /** 车辆Id */
    @ApiModelProperty(value = "车辆Id", position = 19)
    private String vehicleId;
}
