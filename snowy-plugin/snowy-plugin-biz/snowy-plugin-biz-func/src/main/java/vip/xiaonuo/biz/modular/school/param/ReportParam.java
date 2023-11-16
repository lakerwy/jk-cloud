package vip.xiaonuo.biz.modular.school.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ReportParam {
    /** 时间 - 起 */
    @ApiModelProperty(value = "时间 - 起")
    private Date startTime ;

    /** 时间 - 止 */
    @ApiModelProperty(value = "时间 - 止")
    private Date endTime ;
}
