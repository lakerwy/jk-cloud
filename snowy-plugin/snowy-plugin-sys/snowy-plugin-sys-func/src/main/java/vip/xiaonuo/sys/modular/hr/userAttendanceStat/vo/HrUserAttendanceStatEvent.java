package vip.xiaonuo.sys.modular.hr.userAttendanceStat.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class HrUserAttendanceStatEvent {
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
    @ApiModelProperty(value = "打卡是否异常：2 异常，1 正常", position = 6)
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
    private String deleteFlag;

    /** 创建时间 */
    @ApiModelProperty(value = "创建时间", position = 14)
    private Date createTime;

    /** 创建人 */
    @ApiModelProperty(value = "创建人", position = 15)
    private String createUser;

    /** 更新时间 */
    @ApiModelProperty(value = "更新时间", position = 16)
    private Date updateTime;

    /** 更新人 */
    @ApiModelProperty(value = "更新人", position = 17)
    private String updateUser;
}
