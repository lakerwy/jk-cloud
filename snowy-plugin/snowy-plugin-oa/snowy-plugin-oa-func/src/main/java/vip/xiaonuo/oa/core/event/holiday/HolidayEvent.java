package vip.xiaonuo.oa.core.event.holiday;

import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.oa.core.event.EventPayload;

import java.util.List;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/7/5 16:39
 */
@Setter
@Getter
public class HolidayEvent implements EventPayload {
    private String holidayRuleId;
    private List<String> userIds;
}
