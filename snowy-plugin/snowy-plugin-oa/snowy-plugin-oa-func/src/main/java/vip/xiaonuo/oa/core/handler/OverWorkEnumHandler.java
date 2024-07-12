package vip.xiaonuo.oa.core.handler;

import vip.xiaonuo.oa.modular.attendance.rule.enums.OverWorkEnum;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/7/4 10:07
 */
public class OverWorkEnumHandler extends CustomFastjsonTypeHandler<OverWorkEnum>{
    public OverWorkEnumHandler(Class<OverWorkEnum> type) {
        super(OverWorkEnum.class);
    }
}
