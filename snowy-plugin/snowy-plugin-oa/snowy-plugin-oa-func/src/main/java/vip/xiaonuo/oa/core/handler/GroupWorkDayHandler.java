package vip.xiaonuo.oa.core.handler;

import vip.xiaonuo.oa.modular.attendance.group.entity.GroupWorkDay;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/7/4 10:08
 */
public class GroupWorkDayHandler extends CustomFastjsonTypeHandler<GroupWorkDay>{
    public GroupWorkDayHandler(Class<GroupWorkDayHandler> type) {
        super(GroupWorkDay.class);
    }
}
