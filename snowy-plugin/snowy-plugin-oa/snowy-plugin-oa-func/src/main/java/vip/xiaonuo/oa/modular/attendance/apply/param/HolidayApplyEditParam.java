package vip.xiaonuo.oa.modular.attendance.apply.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.oa.modular.attendance.apply.entity.ApplyJson;
import vip.xiaonuo.oa.modular.attendance.apply.entity.HolidayJson;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/7/15 10:05
 */
@Setter
@Getter
public class HolidayApplyEditParam extends ApplyEditParam<HolidayJson>{

    /** 申请类型：1 补卡 2 请假 3 加班 4 外出 5 出差 6 签到 */
    @ApiModelProperty(value = "申请类型：1 补卡 2 请假 3 加班 4 外出 5 出差 6 签到", position = 6)
    private int applyType = 2;

    /** 申请内容JSON */
    @ApiModelProperty(value = "申请内容JSON", position = 9)
    private HolidayJson applyJson;
}
