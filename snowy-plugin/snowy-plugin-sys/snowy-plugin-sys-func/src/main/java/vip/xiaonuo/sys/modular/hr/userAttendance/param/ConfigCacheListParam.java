package vip.xiaonuo.sys.modular.hr.userAttendance.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ConfigCacheListParam {

    /** 人员信息ID */
    @ApiModelProperty(value = "人员信息ID")
    private String userId;

    @ApiModelProperty(value = "时间")
    Date time;

}
