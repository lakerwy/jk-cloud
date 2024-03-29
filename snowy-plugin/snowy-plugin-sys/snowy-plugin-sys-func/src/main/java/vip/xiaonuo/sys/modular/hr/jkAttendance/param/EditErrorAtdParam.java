package vip.xiaonuo.sys.modular.hr.jkAttendance.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EditErrorAtdParam {
    /** 打卡ID*/
    @ApiModelProperty(value = "打卡ID", position = 1)
    private String attendanceId;

    /** 状态修改*/
    @ApiModelProperty(value = "异常状态：-1 缺卡，1 正常，3 迟到， 4 早退，5 严重迟到，6 旷工迟到",required = true,position = 2)
    private String editStatus;

    /** 备注*/
    @ApiModelProperty(value = "备注", position = 3)
    private String remark;
}
