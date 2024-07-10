package vip.xiaonuo.oa.core.event;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import org.springframework.stereotype.Component;
import vip.xiaonuo.oa.core.event.holiday.HolidayEvent;
import vip.xiaonuo.oa.modular.attendance.holiday.service.HolidayBalanceService;

import javax.annotation.Resource;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/7/8 9:19
 */
@Component
public class EventListener {

    @Resource
    HolidayBalanceService holidayBalanceService;

    @org.springframework.context.event.EventListener
    public void handleEvent(Event<?> event) {
        EventPayload payload = event.getPayload();
        if (payload instanceof HolidayEvent) {
            handleHolidayEvent((HolidayEvent) payload);
        } else {

        }
        // 可以添加更多类型的处理逻辑
    }

    private void handleHolidayEvent(HolidayEvent event) {
        DateTime date = DateUtil.date();
        int year = date.year();
        holidayBalanceService.initUserHolidayData(year,event.getHolidayRuleId(),event.getUserIds());
    }
}
