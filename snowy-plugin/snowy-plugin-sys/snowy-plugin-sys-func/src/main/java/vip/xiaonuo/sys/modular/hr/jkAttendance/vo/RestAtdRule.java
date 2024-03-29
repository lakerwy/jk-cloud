package vip.xiaonuo.sys.modular.hr.jkAttendance.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RestAtdRule {
    /** 考勤规则类别 */
    @ApiModelProperty(value = "休息日打卡开始时间", position = 1)
    private String restBeginTime;

    @ApiModelProperty(value = "休息日打卡方式:1 标准打卡模式(第一次是上班，第二次打卡是下班)，2 严格打卡模式", position = 1)
    private String atdType;

    @ApiModelProperty(value = "上班打卡间隔：上班多久后可以下班打卡", position = 1)
    private Integer ondutyAtdGap;

    @ApiModelProperty(value = "下班打卡间隔：上班多久后可以下班打卡", position = 1)
    private Integer offdutyAtdGap;

    @ApiModelProperty(value = "休息日是否打卡审批:1 需要审批", position = 1)
    private Integer isApprove;
}
