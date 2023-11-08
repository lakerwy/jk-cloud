package vip.xiaonuo.sys.modular.hr.conference.rez.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;


@Setter
@Getter
public class ConferenceRoomParam {

    /** 开始时间 */
    @ApiModelProperty(value = "开始时间",required = true)
    @NotNull(message = "开始时间不能为空")
    private String beginTime;

    /** 结束时间 */
    @ApiModelProperty(value = "结束时间",required = true)
    @NotNull(message = "结束时间不能为空")
    private String endTime;
}
