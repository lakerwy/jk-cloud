package vip.xiaonuo.oa.core.handler;

import vip.xiaonuo.oa.modular.attendance.holiday.vo.HolidayDetailVo;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/7/4 10:07
 */
public class HolidayHandler extends CustomFastjsonTypeHandler<HolidayDetailVo>{
    public HolidayHandler(Class<HolidayDetailVo> type) {
        super(HolidayDetailVo.class);
    }
}
