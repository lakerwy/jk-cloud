package vip.xiaonuo.oa.modular.attendance.shift.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;
import java.util.Date;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/6/6 20:58
 * 班次规则类
 */
@Setter
@Getter
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class ClockInRule {
    @ApiModelProperty(value = "打卡类型", position = 1)
    private String type; // 打卡类型，如 "上班", "午休", "下班", "加班"

    @ApiModelProperty(value = "正常打卡开始时间", position = 2)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="HH:mm:ss")
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime normalStart; // 正常打卡开始时间

    @ApiModelProperty(value = "打卡结束时间", position = 3)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="HH:mm:ss")
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime normalEnd; // 正常打卡结束时间

    @ApiModelProperty(value = "最早打卡时间", position = 4)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="HH:mm:ss")
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime earliest; // 最早打卡时间

    @ApiModelProperty(value = "最晚打卡时间", position = 5)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="HH:mm:ss")
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime latest; // 最晚打卡时间

    /**
     * 下班打卡是否允许次日，最大可能用于加班
     */
    @ApiModelProperty(value = "下班打卡是否允许次日", position = 5)
    private Boolean offDutyNextDay = false; // 下班打卡是否是次日

    public void isValid() {
        // 1. 正常打卡时间校验
        if (normalStart != null && normalEnd != null) {
            // 正常打卡开始时间不能晚于或等于结束时间
            if (normalStart.isAfter(normalEnd) || normalStart.equals(normalEnd)) {
                log.info("正常打卡开始时间不能晚于或等于结束时间");
                return;
            }
        }

        // 2. 最早打卡时间校验
        if (earliest != null && normalStart != null) {
            // 最早打卡时间不能晚于正常打卡开始时间
            if (earliest.isAfter(normalStart)) {
                log.info("最早打卡时间不能晚于正常打卡开始时间");
                return;
            }
        }

        // 3. 最晚打卡时间校验
        if (latest != null && normalEnd != null) {
            if (!offDutyNextDay) {
                // 不允许跨天打卡时，最晚打卡时间不能早于正常打卡结束时间
                if (latest.isBefore(normalEnd)) {
                    log.info("最晚打卡时间不能早于正常打卡结束时间");
                }
            } else {
                // 允许跨天打卡时，最晚打卡时间不能晚于次日的最早打卡时间
                if (latest.isAfter(normalEnd.plusHours(24))) {
                    log.info("跨天打卡时，最晚打卡时间不能晚于次日的最早打卡时间");
                }
            }
        }

    }
}
