package vip.xiaonuo.sys.modular.hr.branchRating.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 分公司评级Id参数
 *
 * @author pzh
 * @date  2024/09/03 10:03
 **/
@Getter
@Setter
public class BranchRatingIdParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true)
    @NotBlank(message = "id不能为空")
    private String id;
}
