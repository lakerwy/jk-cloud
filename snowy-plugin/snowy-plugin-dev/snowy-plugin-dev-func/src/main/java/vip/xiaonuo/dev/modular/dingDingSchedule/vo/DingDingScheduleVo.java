package vip.xiaonuo.dev.modular.dingDingSchedule.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DingDingScheduleVo {

    /** 日程创建人Id */
    @ApiModelProperty(value = "日程创建人Id")
    private String unionCreateId;

    /** 钉钉用户ID */
    @ApiModelProperty(value = "钉钉用户ID")
    private List<String> unionIds;

    /** token */
    @ApiModelProperty(value = "token")
    private String xAcsDingtalkAccessToken;

    /** 开始时间 */
    @ApiModelProperty(value = "开始时间")
    private String startTime;

    /** 结束时间 */
    @ApiModelProperty(value = "结束时间")
    private String endTime;

    /** 日程标题 */
    @ApiModelProperty(value = "日程标题")
    private String summary;

    /** 日程描述 */
    @ApiModelProperty(value = "日程描述")
    private String description;

    /** 日程地点 */
    @ApiModelProperty(value = "日程地点")
    private String location;

    /** 日程提前提醒时间，单位：分钟 */
    @ApiModelProperty(value = "日程提前提醒时间，单位：分钟")
    private Integer reminderMinutes;
}
