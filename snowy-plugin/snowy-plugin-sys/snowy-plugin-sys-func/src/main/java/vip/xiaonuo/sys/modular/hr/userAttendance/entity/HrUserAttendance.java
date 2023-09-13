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
package vip.xiaonuo.sys.modular.hr.userAttendance.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 打卡记录表实体
 *
 * @author ljy
 * @date  2023/09/07 14:24
 **/
@Getter
@Setter
@TableName("HR_USER_ATTENDANCE")
public class HrUserAttendance {

    /** 主键 */
    @TableId
    @ApiModelProperty(value = "主键", position = 1)
    private String id;

    /** 人员信息ID */
    @ApiModelProperty(value = "人员信息ID", position = 2)
    private String userId;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 3)
    private String tenantId;

    /** 打卡时间 */
    @ApiModelProperty(value = "打卡时间", position = 4)
    private Date checkTime;

    /** 打卡点位(机位) */
    @ApiModelProperty(value = "打卡点位(机位)", position = 5)
    private String machineCode;

    /** 打卡是否异常：1 异常，0 正常 */
    @ApiModelProperty(value = "打卡是否异常：1 异常，0 正常", position = 6)
    private Integer abnormalFlag;

    /** 上下班标识；OnDuty：上班；OffDuty：下班 */
    @ApiModelProperty(value = "上下班标识；OnDuty：上班；OffDuty：下班；休息 REST", position = 7)
    private String checkType;

    /** 打卡地址对应的纬度 */
    @ApiModelProperty(value = "打卡地址对应的纬度", position = 8)
    private String latitude;

    /** 打卡地址对应的经度 */
    @ApiModelProperty(value = "打卡地址对应的经度", position = 9)
    private String longitude;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 10)
    private String extJson;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 11)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 12)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 13)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 修改时间  */
    @ApiModelProperty(value = "修改时间 ", position = 14)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 15)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;
}
