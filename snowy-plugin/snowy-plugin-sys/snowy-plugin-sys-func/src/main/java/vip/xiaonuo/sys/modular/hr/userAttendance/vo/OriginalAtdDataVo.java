package vip.xiaonuo.sys.modular.hr.userAttendance.vo;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.sys.modular.hr.userAttendance.entity.HrUserAttendance;

import java.util.List;
import java.util.Map;

@Setter
@Getter
public class OriginalAtdDataVo {
    /** 用户ID*/
    @ApiModelProperty(value = "用户ID", position = 1)
    private String userId;

    /** 用户打卡数据*/
    @ApiModelProperty(value = "用户打卡数据", position = 2)
    List<HrUserAttendance> attendanceList;

    /** 用户考勤*/
    @ApiModelProperty(value = "用户考勤", position = 3)
    Map<String, Integer> mapType;
}
