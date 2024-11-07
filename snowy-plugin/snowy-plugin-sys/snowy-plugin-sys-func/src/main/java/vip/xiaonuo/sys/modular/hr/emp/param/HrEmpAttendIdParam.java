package vip.xiaonuo.sys.modular.hr.emp.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 员工月度考勤表 - 单独 - 非工资系统Id参数
 *
 * @author yong.ye
 * @date  2024/11/06 16:53
 **/
@Getter
@Setter
public class HrEmpAttendIdParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true)
    @NotBlank(message = "id不能为空")
    private String id;
}
