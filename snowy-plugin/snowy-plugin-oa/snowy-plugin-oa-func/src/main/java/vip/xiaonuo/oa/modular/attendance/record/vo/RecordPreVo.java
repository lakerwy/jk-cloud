package vip.xiaonuo.oa.modular.attendance.record.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/6/13 17:35
 */
@Getter
@Setter
public class RecordPreVo {

    @ApiModelProperty(value = "打卡类型", position = 2)
    private String type; //打卡类型

    @ApiModelProperty(value = "正常打卡开始时间", position = 2)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="HH:mm:ss")
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime normalStart; // 正常打卡开始时间

}
