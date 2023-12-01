package vip.xiaonuo.sys.modular.hr.conference.rez.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ConfirmStat {

    /** 主键 */
    @ApiModelProperty(value = "主键", position = 1)
    private String id;

    /** 会议纪要确认人ID */
    @ApiModelProperty(value = "会议纪要确认人ID", position = 1)
    private String confirmUserId;

    /** 是否对会议进行确认，1 已确认 */
    @ApiModelProperty(value = "是否对会议进行确认，1 已确认", position = 2)
    private Integer status;

    /** 会议记录ID */
    @ApiModelProperty(value = "会议记录ID", position = 3)
    private String recordId;
}
