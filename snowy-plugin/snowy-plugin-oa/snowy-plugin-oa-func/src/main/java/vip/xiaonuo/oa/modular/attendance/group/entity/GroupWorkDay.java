package vip.xiaonuo.oa.modular.attendance.group.entity;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import vip.xiaonuo.oa.modular.attendance.shift.entity.Shift;
import vip.xiaonuo.oa.modular.attendance.shift.entity.Shift;

import java.util.Date;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/6/4 17:30
 */
@Setter
@Getter
public class GroupWorkDay {

    @ApiModelProperty(value = "周几(sunday-1 、monday -2、tuesday -3、wednesday -4、thursday -5、friday -6、saturday -7)(特殊日期无需使用)", position = 1)
    private int weekDay;

    @ApiModelProperty(value = "是否需要打卡", position = 2)
    private boolean checked;

    @ApiModelProperty(value = "班次id", position = 3)
    private String shiftId;

    @ApiModelProperty(value = "班次对象", position = 4)
    private String shiftName;

    @ApiModelProperty(value = "日期(特殊日期的时候设置)",position = 5)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern= DatePattern.NORM_DATE_PATTERN)
    @DateTimeFormat(pattern = DatePattern.NORM_DATE_PATTERN)
    private Date workDay;
}
