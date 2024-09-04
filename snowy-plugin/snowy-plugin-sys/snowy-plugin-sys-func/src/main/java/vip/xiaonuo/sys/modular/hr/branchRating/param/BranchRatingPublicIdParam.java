package vip.xiaonuo.sys.modular.hr.branchRating.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 分公司评级-公示年份和季度Id参数
 *
 * @author pzh
 * @date  2024/09/04 10:21
 **/
@Getter
@Setter
public class BranchRatingPublicIdParam {

    /** 主键ID */
    @ApiModelProperty(value = "主键ID", required = true)
    @NotBlank(message = "id不能为空")
    private String id;
}
