package vip.xiaonuo.oa.modular.attendance.apply.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/7/15 10:29
 */
@Setter
@Getter
@ToString
public class HolidayJson extends ApplyJson {

    @ApiModelProperty(value = "假期类型Id", position = 1)
    private String holidayTypeId;

    @ApiModelProperty(value = "假期类型", position = 2)
    private String holidayType;

    @ApiModelProperty(value = "请假开始时间", position = 3)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date applyStart;

    @ApiModelProperty(value = "请假结束时间", position = 4)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date applyEnd;

    @ApiModelProperty(value = "请假时长(单位:天)", position = 5)
    private Double duration;

    @ApiModelProperty(value = "事由", position = 6)
    private String remark;

    @ApiModelProperty(value = "附件1", position = 7)
    private String attachment1;

    @ApiModelProperty(value = "附件2", position = 8)
    private String attachment2;
}
