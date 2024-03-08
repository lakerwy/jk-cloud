package vip.xiaonuo.dev.modular.dingDingSchedule.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DingDingScheduleParam {

    /** 用户ID */
    @ApiModelProperty(value = "用户ID")
    private String userId;

    /** 用户名 */
    @ApiModelProperty(value = "用户名")
    private String userName;

    /** 用户电话号码 */
    @ApiModelProperty(value = "用户电话号码")
    private String phone;
}
