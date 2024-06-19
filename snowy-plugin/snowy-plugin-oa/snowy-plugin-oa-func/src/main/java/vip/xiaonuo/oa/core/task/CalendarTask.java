package vip.xiaonuo.oa.core.task;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import vip.xiaonuo.oa.modular.attendance.shift.service.CalendarService;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.Date;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/6/15 13:11
 * 日历同步任务
 */
@Component
@Slf4j
public class CalendarTask extends AbstractTask{

    @Resource
    private CalendarService calendarService;

//    @PostConstruct
//    public void init() {
//       scheduleTask();
//    }

    public CalendarTask() {
        super("CalendarTask");
    }

    @Override
    protected void doTask() throws Exception {
        log.info("每年12月28凌晨1点定时同步下一年的节假日信息，同步节假日开始时间 = {}", DateUtil.formatDateTime(new Date()));
        int nextYear = LocalDate.now().getYear() + 1;
        calendarService.generateAndSaveCalendar(nextYear);
        log.info("每年12月28凌晨1点定时同步下一年的节假日信息，同步节假日结束时间 = {}", DateUtil.formatDateTime(new Date()));
    }

    @Scheduled(cron = "0 0 1 28 12 ?") // 每年12月28凌晨1点
    public void scheduleTask() {
        execute();
    }
}
