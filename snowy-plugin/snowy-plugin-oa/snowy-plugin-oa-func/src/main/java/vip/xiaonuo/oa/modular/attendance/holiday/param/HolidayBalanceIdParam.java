package vip.xiaonuo.oa.modular.attendance.holiday.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 假期余额管理表Id参数
 *
 * @author mayike
 * @date  2024/07/05 10:57
 **/
@Getter
@Setter
public class HolidayBalanceIdParam {

    /** 主键 */
    @ApiModelProperty(value = "主键", required = true)
    @NotBlank(message = "id不能为空")
    private String id;
}
