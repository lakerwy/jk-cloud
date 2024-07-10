package vip.xiaonuo.oa.modular.attendance.holiday.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/7/5 14:52
 */
@Setter
@Getter
public class HolidayDetailVo {

    @ApiModelProperty(value = "假期管理主键ID", position = 1)
    private String holidayBalanceId;

    @ApiModelProperty(value = "假期类型ID", position = 1)
    private String holidayTypeId;

    @ApiModelProperty(value = "假期名称", position = 1)
    private String holidayName;

    @ApiModelProperty(value = "假期余额", position = 5)
    private BigDecimal balance;
}
