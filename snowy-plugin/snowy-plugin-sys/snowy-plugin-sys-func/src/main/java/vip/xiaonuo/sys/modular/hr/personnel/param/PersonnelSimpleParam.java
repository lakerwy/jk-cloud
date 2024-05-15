package vip.xiaonuo.sys.modular.hr.personnel.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author mayike
 * @version 1.0
 * @date 2024/5/15 10:57
 */
@Setter
@Getter
public class PersonnelSimpleParam {
    /** 主键ID */
    @ApiModelProperty(value = "主键userId", required = true, position = 1)
    @NotBlank(message = "主键userId不能为空")
    private String userId;

    /** 部门ID */
    @ApiModelProperty(value = "部门ID", position = 4)
    private String deptId;

    /** 部门 */
    @ApiModelProperty(value = "部门", position = 5)
    private String dept;

    /** 职位ID */
    @ApiModelProperty(value = "职位ID", position = 6)
    private String dutyId;

    /** 职位 */
    @ApiModelProperty(value = "职位", position = 7)
    private String duty;
}
