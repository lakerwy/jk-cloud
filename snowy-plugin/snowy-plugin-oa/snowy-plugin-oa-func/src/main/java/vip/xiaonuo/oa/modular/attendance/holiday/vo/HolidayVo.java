package vip.xiaonuo.oa.modular.attendance.holiday.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/7/5 14:42
 */
@Setter
@Getter
public class HolidayVo {

    @ApiModelProperty(value = "用户Id", position = 1)
    private String userId;

    @ApiModelProperty(value = "用户姓名", position = 2)
    private String userName;

    @ApiModelProperty(value = "部门Id", position = 3)
    private String orgId;

    @ApiModelProperty(value = "部门名称", position = 4)
    private String orgName;

    @ApiModelProperty(value = "假期余额列表", position = 5)
    private List<HolidayDetailVo> holidayDetails;
}
