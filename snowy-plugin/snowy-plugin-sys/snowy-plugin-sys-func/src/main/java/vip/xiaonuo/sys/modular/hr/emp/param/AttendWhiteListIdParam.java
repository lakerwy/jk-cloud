package vip.xiaonuo.sys.modular.hr.emp.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 钉钉考勤-员工白名单Id参数
 *
 * @author pzh
 * @date  2025/03/06 11:34
 **/
@Getter
@Setter
public class AttendWhiteListIdParam {

    /** 主键 */
    @ApiModelProperty(value = "主键", required = true)
    @NotBlank(message = "id不能为空")
    private String id;
}
