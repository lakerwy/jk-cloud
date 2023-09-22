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
package vip.xiaonuo.sys.modular.hr.userAttendanceConfig.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 考勤配置
 *
 * @author ljy
 * @date  2023/09/07 13:07
 **/
@Getter
@Setter
public class HrUserAttendanceConfigEntity {
    /** 主键id */
    @TableId
    @ApiModelProperty(value = "主键id", position = 1)
    private String id;

    /** 租户id */
    @ApiModelProperty(value = "租户id", position = 2)
    private String tenantId;

    /** 排班顺序 */
    @ApiModelProperty(value = "排班顺序", position = 3)
    private Integer orderNumber;

    /** 考勤班次类别 */
    @ApiModelProperty(value = "考勤班次类别名", position = 4)
    private String attendanceTypeName;

    /** 班次打卡类别 */
    @ApiModelProperty(value = "班次打卡类别 (上班OnDuty，下班 OffDuty，休息 REST)", position = 4)
    private String dutyType;

    /** 允许最早多少时间打卡 */
    @ApiModelProperty(value = "允许最早多少时间打卡", position = 5)
    private String BeginMinTime;

    /** 打卡时间 */
    @ApiModelProperty(value = "打卡时间", position = 6)
    private String checkTime;

    /** 允许最晚多少时间打卡 */
    @ApiModelProperty(value = "允许最晚多少时间打卡", position = 7)
    private String endMinTime;

    /** 作用范围区域  */
    @ApiModelProperty(value = "作用范围区域 ", position = 8)
    private String actionRange;

    /** 区域范围是否有效 */
    @ApiModelProperty(value = "区域范围是否有效", position = 9)
    private Integer isValidRange;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 10)
    private String extJson;

    /** 删除标志 */
    @ApiModelProperty(value = "删除标志", position = 11)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间  */
    @ApiModelProperty(value = "创建时间 ", position = 12)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建用户 */
    @ApiModelProperty(value = "创建用户", position = 13)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 修改时间 */
    @ApiModelProperty(value = "修改时间", position = 14)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 修改用户 */
    @ApiModelProperty(value = "修改用户", position = 15)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /** 班次工时，单位：小时 */
    @ApiModelProperty(value = "班次工时，单位：小时", position = 16)
    private String workHours;

    /** 考勤班次类别组ID */
    @ApiModelProperty(value = "考勤班次类别组ID", position = 17)
    private String attendanceTypeId;
}
