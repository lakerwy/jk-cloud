package vip.xiaonuo.oa.core.handler;

import vip.xiaonuo.oa.modular.attendance.group.entity.AttObject;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/7/4 10:07
 */
public class AttObjectHandler extends CustomFastjsonTypeHandler<AttObject>{
    public AttObjectHandler(Class<AttObject> type) {
        super(AttObject.class);
    }
}
