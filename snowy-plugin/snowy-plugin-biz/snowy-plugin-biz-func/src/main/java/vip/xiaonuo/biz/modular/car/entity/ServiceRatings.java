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
 * CAR-用车服务评价实体
 *
 * @author yong.ye
 * @date  2023/11/08 16:03
 **/
@Getter
@Setter
@TableName("BIZ_CAR_SERVICE_RATINGS")
public class ServiceRatings {

    /** 主键ID */
    @TableId
    @ApiModelProperty(value = "主键ID", position = 1)
    private String id;

    /** 租户Id */
    @ApiModelProperty(value = "租户Id", position = 2)
    private String tenantId;

    /** 用车申请-主键Id  */
    @ApiModelProperty(value = "用车申请-主键Id ", position = 3)
    private String vehicleApplyId;

    /** 安全驾驶 */
    @ApiModelProperty(value = "安全驾驶", position = 4)
    private Double driverSafe;

    /** 服务意识 */
    @ApiModelProperty(value = "服务意识", position = 5)
    private Double serviceWare;

    /** 车辆卫生 */
    @ApiModelProperty(value = "车辆卫生", position = 6)
    private Double hygiene;

    /** 乘坐感受 */
    @ApiModelProperty(value = "乘坐感受", position = 7)
    private Double rideFeel;

    /** 总体评价 */
    @ApiModelProperty(value = "总体评价", position = 8)
    private Double overallEvaluation;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 9)
    private String memo;

    /** 删除状态标记 */
    @ApiModelProperty(value = "删除状态标记", position = 10)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 11)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty(value = "创建人", position = 12)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间", position = 13)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty(value = "更新人", position = 14)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;
}
