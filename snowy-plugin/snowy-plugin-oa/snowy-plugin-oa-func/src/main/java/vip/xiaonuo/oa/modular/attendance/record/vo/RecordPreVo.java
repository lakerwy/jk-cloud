package vip.xiaonuo.oa.modular.attendance.record.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import vip.xiaonuo.oa.modular.attendance.record.entity.Record;

import java.time.LocalTime;
import java.util.List;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/6/13 17:35
 */
@Getter
@Setter
public class RecordPreVo {

    @ApiModelProperty(value = "是否允许外勤打卡",position = 1)
    private Boolean allowFieldWork;

    @ApiModelProperty(value = "外勤打卡备注是否必填",position = 2)
    private Boolean requiredFieldWorkRemarks;

    @ApiModelProperty(value = "当前时间是否能够打卡",position = 3)
    private Boolean canCheckIn;

    @ApiModelProperty(value = "不能打卡的原因",position = 4)
    private String cannotCheckInReason;

    @ApiModelProperty(value = "打卡列表", position = 5)
    private List<Record> records;
}
