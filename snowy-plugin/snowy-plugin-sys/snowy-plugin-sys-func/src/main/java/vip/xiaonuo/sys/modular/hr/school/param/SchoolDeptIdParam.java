package vip.xiaonuo.sys.modular.hr.school.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 校招 - （应聘者）- 推送部门流程Id参数
 *
 * @author yong.ye
 * @date  2024/09/03 10:47
 **/
@Getter
@Setter
public class SchoolDeptIdParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true)
    @NotBlank(message = "id不能为空")
    private String id;
}
