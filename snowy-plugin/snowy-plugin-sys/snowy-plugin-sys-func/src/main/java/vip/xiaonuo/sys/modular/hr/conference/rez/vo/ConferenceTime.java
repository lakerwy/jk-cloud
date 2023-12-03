package vip.xiaonuo.sys.modular.hr.conference.rez.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class ConferenceTime {

    /** 时间：时-分*/
    @ApiModelProperty(value = "时间:时-分")
    private String time;

    /** 状态 */
    @ApiModelProperty(value = "状态，1 空闲，2 占有")
    private Integer status;

    /** 开始时间 */
    @ApiModelProperty(value = "开始时间")
    private Date beginTime;

    /** 结束时间 */
    @ApiModelProperty(value = "结束时间")
    private Date endTime;

    /** 预约人 */
    @ApiModelProperty(value = "预约人")
    private String rezUserID;

    /** 预约人 */
    @ApiModelProperty(value = "预约人")
    private String rezUserName;

    /** 会议ID */
    @ApiModelProperty(value = "会议ID")
    private String rezId;
}
