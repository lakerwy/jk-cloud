package vip.xiaonuo.sys.modular.dingtalk.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 同步钉钉部门信息 - 指定部门
 */

@Getter
@Setter
public class SyncDeptParam {

    /** 部门名称 */
    @ApiModelProperty(value = "部门名称", position = 1,required = true)
    @NotBlank(message = "部门名称不能为空")
    private String deptName;

    /** 上级部门Id */
    @ApiModelProperty(value = "上级部门Id", position = 2,required = true)
    @NotBlank(message = "上级部门Id不能为空")
    private String parentDeptId;

}
