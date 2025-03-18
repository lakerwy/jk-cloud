package vip.xiaonuo.sys.modular.hr.branchRating.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 分公司评级（二期）- 区域经济修正系数 Id参数
 *
 * @author yong.ye
 * @date  2025/03/18 08:47
 **/
@Getter
@Setter
public class BranchAreaIdParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true)
    @NotBlank(message = "id不能为空")
    private String id;
}
