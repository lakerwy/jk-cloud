package vip.xiaonuo.oa.modular.attendance.holiday.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 假期余额管理表添加参数
 *
 * @author mayike
 * @date  2024/07/05 10:57
 **/
@Getter
@Setter
public class HolidayBalanceAddParam {

    /** 员工ID */
    @ApiModelProperty(value = "员工ID", position = 2)
    private String userId;

    @ApiModelProperty(value = "年份", position = 3)
    private int year;

    /** 假期类型ID */
    @ApiModelProperty(value = "假期类型ID", position = 3)
    private String holidayTypeId;

    /** 假期余额 */
    @ApiModelProperty(value = "假期余额", position = 4)
    private BigDecimal balance;

    /** 备注 */
    @ApiModelProperty(value = "备注", position = 5)
    private String remark;

    /** 扩展信息 */
    @ApiModelProperty(value = "扩展信息", position = 6)
    private String extJson;

}
