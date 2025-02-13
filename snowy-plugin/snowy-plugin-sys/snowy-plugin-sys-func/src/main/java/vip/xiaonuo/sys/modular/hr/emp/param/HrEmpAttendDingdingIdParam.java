package vip.xiaonuo.sys.modular.hr.emp.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 员工月度考勤(钉钉)Id参数
 *
 * @author pzh
 * @date  2025/02/12 15:17
 **/
@Getter
@Setter
public class HrEmpAttendDingdingIdParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true)
    @NotBlank(message = "id不能为空")
    private String id;
}
