package vip.xiaonuo.oa.core.handler;

import vip.xiaonuo.oa.modular.attendance.group.entity.WorkPlace;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/7/4 10:09
 */
public class WorkPlaceHandler extends CustomFastjsonTypeHandler<WorkPlace>{
    public WorkPlaceHandler(Class<WorkPlace> type) {
        super(WorkPlace.class);
    }
}
