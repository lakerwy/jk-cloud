package vip.xiaonuo.oa.modular.attendance.shift.entity;

import cn.hutool.core.date.DateUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;

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
public class Rule {
    @ApiModelProperty(value = "打卡类型", position = 1)
    private String type; // 打卡类型，如 "上班", "午休", "下班", "加班"

    @ApiModelProperty(value = "正常打卡时间", position = 2)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="HH:mm:ss")
    @DateTimeFormat(pattern = "HH:mm:ss")
    private Date normal; // 正常打卡开始时间

    @ApiModelProperty(value = "最早打卡时间", position = 4)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="HH:mm:ss")
    @DateTimeFormat(pattern = "HH:mm:ss")
    private Date earliest; // 最早打卡时间

    @ApiModelProperty(value = "最晚打卡时间", position = 5)
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="HH:mm:ss")
    @DateTimeFormat(pattern = "HH:mm:ss")
    private Date latest; // 最晚打卡时间

    @ApiModelProperty(value = "允许早退/迟到的时长，单位分钟", position = 6)
    private int permitMinutes;

    @ApiModelProperty(value = "旷工早退/迟到的时长，单位分钟", position = 6)
    private int absenteeismLateMinutes;

    @ApiModelProperty(value = "严重早退/迟到的时长，单位分钟", position = 6)
    private int seriousLateMinutes;

    /**
     * 下班打卡是否允许次日，最大可能用于加班
     */
    @ApiModelProperty(value = "下班打卡是否允许次日", position = 5)
    private Boolean offDutyNextDay = false; // 下班打卡是否是次日

    public void isValid() {
        // 1. 正常打卡时间校验
        if (normal != null) {
            // 正常打卡开始时间不能晚于或等于结束时间
            if (normal.after(latest)) {
                log.info("正常打卡开始时间不能晚于");
                return;
            }
        }

        // 2. 最早打卡时间校验
        if (earliest != null && normal != null) {
            // 最早打卡时间不能晚于正常打卡开始时间
            if (earliest.after(normal)) {
                log.info("最早打卡时间不能晚于正常打卡开始时间");
                return;
            }
        }

        // 3. 最晚打卡时间校验
        if (latest != null && earliest != null) {
            if (!offDutyNextDay) {
                // 不允许跨天打卡时，最晚打卡时间不能早于正常打卡结束时间
                if (latest.before(normal)) {
                    log.info("最晚打卡时间不能早于正常打卡结束时间");
                }
            } else {
                // 允许跨天打卡时，最晚打卡时间不能晚于次日的最早打卡时间
                if (latest.after(DateUtil.offsetHour(normal,24))) {
                    log.info("跨天打卡时，最晚打卡时间不能晚于次日的最早打卡时间");
                }
            }
        }

    }
}
