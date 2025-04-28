package vip.xiaonuo.sys.modular.hr.conference.rez.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ConferenceRezVo {
    /** 主键 */
    @ApiModelProperty(value = "主键", position = 1)
    private String id;

    /** 预约主题 */
    @ApiModelProperty(value = "预约主题", position = 2)
    private String theme;

    /** 开始时间 */
    @ApiModelProperty(value = "开始时间", position = 3)
    private Date beginTime;

    /** 结束时间 */
    @ApiModelProperty(value = "结束时间", position = 4)
    private Date endTime;

    /** 预约人 */
    @ApiModelProperty(value = "预约人", position = 5)
    private String userName;

    /** 会议室名称 */
    @ApiModelProperty(value = "会议室名称", position = 6)
    private String roomName;

}
