package vip.xiaonuo.oa.modular.attendance.group.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import vip.xiaonuo.oa.modular.attendance.shift.entity.Shift;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/6/4 17:30
 */
@Setter
@Getter
public class GroupWorkDay {

    @ApiModelProperty(value = "是否需要打卡", position = 1)
    private boolean checked; //
    @ApiModelProperty(value = "班次id", position = 2)
    private String shiftId; //
    @ApiModelProperty(value = "班次对象", position = 2) // 不存储，返回前端的时候提供
    private Shift shift;
}
