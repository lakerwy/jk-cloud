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
package vip.xiaonuo.sys.modular.hr.userAttendanceStat.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 考勤统计表实体
 *
 * @author ljy
 * @date  2023/10/07 10:28
 **/
@Getter
@Setter
@TableName("HR_USER_ATTENDANCE_STAT")
public class HrUserAttendanceStat {

    /** 主键 */
    @TableId
    @ApiModelProperty(value = "主键", position = 1)
    private String id;

    /** 人员ID */
    @ApiModelProperty(value = "人员ID", position = 2)
    private String userId;

    /** 用户姓名 */
    @ApiModelProperty(value = "用户姓名", position = 3)
    private String userName;

    /** 打卡时间 */
    @ApiModelProperty(value = "打卡时间", position = 4)
    private Date checkTime;

    /** 打卡机器code */
    @ApiModelProperty(value = "打卡机器code", position = 5)
    private String machineCode;

    /** 打卡是否异常：2 异常，1 正常 */
    @ApiModelProperty(value = "打卡是否异常：-1 缺卡，1 正常,3 迟到，4 早退，5 请假，6 管理员修改 ", position = 6)
    private Integer abnormalFlag;

    /** 上下班标识；OnDuty：上班；OffDuty：下班；休息 REST */
    @ApiModelProperty(value = "上下班标识；OnDuty：上班；OffDuty：下班；休息 REST", position = 7)
    private String checkType;

    /** 打卡地址对应的纬度 */
    @ApiModelProperty(value = "打卡地址对应的纬度", position = 8)
    private String latitude;

    /** 打卡地址对应的经度 */
    @ApiModelProperty(value = "打卡地址对应的经度", position = 9)
    private String longitude;

    /** 是否申请补卡标识 */
    @ApiModelProperty(value = "是否申请补卡标识", position = 10)
    private String isResubmitAttendanceFlag;

    /** 申请补卡ID */
    @ApiModelProperty(value = "申请补卡ID", position = 11)
    private String applyResubmitAttendanceId;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 12)
    private String extJson;

    /** 删除标识 */
    @ApiModelProperty(value = "删除标识", position = 13)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 14)
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty(value = "创建人", position = 15)
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间", position = 16)
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty(value = "更新人", position = 17)
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;
}
