package vip.xiaonuo.biz.modular.deptFee.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 部门费用管理Id参数
 *
 * @author pzh
 * @date  2025/06/05 14:56
 **/
@Getter
@Setter
public class DeptFeeIdParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true)
    @NotBlank(message = "id不能为空")
    private String id;
}
