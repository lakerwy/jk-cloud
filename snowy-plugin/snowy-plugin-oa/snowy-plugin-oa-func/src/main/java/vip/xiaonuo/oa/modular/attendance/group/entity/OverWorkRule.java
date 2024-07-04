package vip.xiaonuo.oa.modular.attendance.group.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;
import java.util.Date;

/**
 * @author mayike
 * @version 1.0  加班规则
 * @date 2024/7/3 10:21
 */
@Setter
@Getter
public class OverWorkRule {

    @ApiModelProperty(value = "加班规则类型（1、加班时长按审批时间为准 2、加班时长已打卡为准）")
    private int ruleType;

    @ApiModelProperty(value = "加班开始时间")
//    @JsonFormat(locale="zh", timezone="GMT+8", pattern="HH:mm:ss")
//    @DateTimeFormat(pattern = "HH:mm:ss")
    private String overWorkStartTime;
}
